
#python3
import sys,os,time,shutil
current_path = os.path.split(os.path.realpath(__file__))[0]
current_sln_path = "%s/csproj" % current_path
current_project_path = "%s/BuildProtocol" % current_sln_path
current_final_gen_path = "%s/bin/Release" % current_project_path
csharp_source_path = "%s/csharp" % current_path
output_path = "%s/output" % current_path

os.chdir(current_path)

log_file = open("log.txt", "w")


def get_exclude_list():
	exclude_list = []
	with open("%s/./exclude_list.txt" % (current_path), "rb") as f:
		for line in f:
			read_data = line.decode('UTF-8').strip()
			exclude_list.append(read_data)
			print(read_data)
			log_file.write("\n exclude:%s" % read_data)
		f.close()
	return exclude_list

def generate():
	rm_old_cs()
	generate_cs()
	move_cs_to_project()
	modify_csproj()
	ms_build()
	final_generate()
	output()

def rm_old_cs():
	print("Start Rm c# sources:")
	if os.path.exists(csharp_source_path):
		shutil.rmtree(csharp_source_path)
	os.mkdir(csharp_source_path)

def execute_command(command):
	import subprocess
	output = subprocess.getoutput(command)
	print("command %s" % command)
	print(output)
	log_file.write(output)

def generate_cs():
	print("Start generate cs")
	#commands = ["gen_proto_source_folder.bat"]
	#[execute_command(cmd) for cmd in commands]

	proto_file_path = "%s/proto" % current_path
	csharp_proto_exe= "%s/library\protobuf-net\ProtoGen\protogen.exe" % current_path
	csharp_save_path= csharp_source_path
	print("csharp_save_path: ", csharp_save_path)

	print("proto_file_path", proto_file_path)
	for root, dirs, files in os.walk(proto_file_path):
		for name in files:
			src_path = os.path.join(root, name)
			#os.chdir(proto_file_path)
			relative_path = os.path.relpath(src_path, proto_file_path)
			dest_path = os.path.join(csharp_save_path, relative_path).replace(".proto", ".cs")

			print("dest", dest_path)
			
			proto_dir = os.path.relpath(root, proto_file_path)


			print("proto_dir", proto_dir)
			cs_save_dir = os.path.join(csharp_save_path, proto_dir)
			print("cs_save_dir", cs_save_dir)
			
			if not os.path.exists(cs_save_dir):
				os.makedirs(cs_save_dir)
			#  %csharp_proto_exe% -p:detectMissing -i:%%s -o:%csharp_save_path%%%A\%%~ns.cs 
			os.system("%s -p:detectMissing -i:%s -o:%s" %(csharp_proto_exe, src_path, dest_path))


def move_cs_to_project():
	print("Start move cs to project:")
	src_dir = "%s/csharp" % current_path
	dst_dir = "%s/csharp" % current_project_path

	if os.path.exists(dst_dir):
		shutil.rmtree(dst_dir)
	
	print("%s deleted!" % dst_dir)
	shutil.copytree(src_dir, dst_dir)
	print("%s copyed!" % dst_dir)


def modify_csproj():
	print("Start modify protobuf project for build")
	template_file = "%s/BuildProtocol.csproj.template" % current_project_path
	csproj_file = "%s/BuildProtocol.csproj" % current_project_path
	root_dir = csharp_source_path
	exclude_cs_list = get_exclude_list()

	cs_sources = []
	for root, dirs, files in os.walk(root_dir):
		for name in files:
			full_path = os.path.join(root, name)
			relative_name = full_path.split(str(root_dir))[1]
			in_cs_list_str = "%s in exclude_list %s" % (relative_name, relative_name in exclude_cs_list)
			log_file.write("\n%s" % in_cs_list_str)
			if relative_name in exclude_cs_list:
				continue
			include_name =  source_line(relative_name)
			log_file.write("\ninclude_name : %s " % include_name)
			cs_sources.append(include_name)

    #import pickle
	source_strs = "\n".join(cs_sources)
	#print("sources: \n %s " % source_strs)
	with open(template_file, "rb") as f:
		read_data = f.read()
		read_data = read_data.decode('UTF-8')
		
		write_data = read_data % source_strs
		#write_data = write_data.encode('UTF-8')
		cs_f = open(csproj_file, "wt", encoding="UTF-8")
		cs_f.write(write_data)
		f.close()
		cs_f.close()



def source_line(name):
	return "    <Compile Include=\"csharp%s\" />"  % name


def ms_build():
	print("Start build protobuf dll:")
	command = "MSBuild %s/BuildProtocol.sln /p:Configuration=Release" % (current_sln_path)
	execute_command(command)


def final_generate():
	print("Start precompile ProtoSerializer")
	os.chdir(current_final_gen_path)
	execute_command("autogen.bat")


def output():
	print("Start copy dll to target dir")
	if os.path.exists(output_path):
		shutil.rmtree(output_path)
	os.mkdir(output_path)
	file1 = "Protocol.dll"
	file2 = "ProtoSerializer.dll"
	shutil.copy("%s/%s" % (current_final_gen_path, file1), "%s/%s" % (output_path, file1) )
	shutil.copy("%s/%s" % (current_final_gen_path, file2), "%s/%s" % (output_path, file2) )
	
	client_path = os.environ.get("ClientProjectPath")
	print("client_path: ", client_path)

	project_name = "longjiang2_logic"
	target_folder_name = "output"
	log_file.write("\ncopy到客户端工程，路径 %s" % ("%s/%s/%s/%s/%s" % (client_path,project_name,project_name,target_folder_name,file1)))
	log_file.write("\ncopy到客户端工程，路径 %s" % ("%s/%s/%s/%s/%s" % (client_path,project_name,project_name,target_folder_name,file2)))

	shutil.copy("%s/%s" % (current_final_gen_path, file1), "%s/%s/%s/%s/%s" % (client_path, project_name, project_name,target_folder_name, file1) )
	shutil.copy("%s/%s" % (current_final_gen_path, file2), "%s/%s/%s/%s/%s" % (client_path, project_name, project_name,target_folder_name, file2) )

	print("copy completed!")

if __name__ == "__main__":

	generate()
	execute_command("pause")
	print("Done!")
