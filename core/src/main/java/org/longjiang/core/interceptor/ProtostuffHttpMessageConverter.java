package org.longjiang.core.interceptor;

import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.base.Stopwatch;
import net.jpountz.lz4.LZ4BlockOutputStream;
import org.alan.mars.rpc.protocol.SerializationUtil;
import org.apache.commons.io.IOUtils;
import org.longjiang.core.result.GeneralResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.xerial.snappy.SnappyOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

/**
 * Created on 2017/6/1.
 *
 * @author Alan
 * @since 1.0
 */
public class ProtostuffHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final MediaType MEDIA_TYPE = new MediaType("application", "protobuf", DEFAULT_CHARSET);
    public static final MediaType MEDIA_TYPE_LZ4 = new MediaType("application", "protobuf-lz4", DEFAULT_CHARSET);
    public static final MediaType MEDIA_TYPE_GZIP = new MediaType("application", "protobuf-gzip", DEFAULT_CHARSET);
    public static final MediaType MEDIA_TYPE_SNAPPY = new MediaType("application", "protobuf-snappy",
            DEFAULT_CHARSET);

    /**
     * Construct a new instance.
     */
    public ProtostuffHttpMessageConverter() {
        super(MEDIA_TYPE, MEDIA_TYPE_LZ4, MEDIA_TYPE_GZIP, MEDIA_TYPE_SNAPPY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
        return canRead(mediaType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object readInternal(final Class<?> clazz, final HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        if (MEDIA_TYPE.isCompatibleWith(inputMessage.getHeaders().getContentType())) {
            final Schema<?> schema = RuntimeSchema.getSchema(clazz);

            final Object value = schema.newMessage();

            try (final InputStream stream = inputMessage.getBody()) {
                ProtobufIOUtil.mergeFrom(stream, value, (Schema<Object>) schema);
                return value;
            }
        }

        throw new HttpMessageNotReadableException(
                "Unrecognized HTTP media type " + inputMessage.getHeaders().getContentType().getType() + ".");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean supports(final Class<?> clazz) {
        // Should not be called, since we override canRead/canWrite.
        return true;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeInternal(final Object o, final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        logger.info("Current type: {}", outputMessage.getHeaders().getContentType());
        Stopwatch stopwatch = Stopwatch.createStarted();
        OutputStream stream = null;
        HttpHeaders httpHeaders = outputMessage.getHeaders();
        try {
            stream = outputMessage.getBody();
            if (MEDIA_TYPE.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
            } else if (MEDIA_TYPE_GZIP.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                stream = new GZIPOutputStream(stream);
            } else if (MEDIA_TYPE_LZ4.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                stream = new LZ4BlockOutputStream(stream);
            } else if (MEDIA_TYPE_SNAPPY.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                stream = new SnappyOutputStream(stream);
            } else {
                throw new HttpMessageNotWritableException(
                        "Unrecognized HTTP media type " + outputMessage.getHeaders().getContentType().getType() + ".");
            }

            if (o instanceof GeneralResult) {
                GeneralResult result = (GeneralResult) o;
                if (result.getData()!=null) {
                    byte[] data = SerializationUtil.serialize(result.getData());
                    result.setPb(data);
                }
            }
            byte[] data = SerializationUtil.serialize(o);
            //data= XXTEA.encrypt(data,)
            httpHeaders.setContentLength(data.length);
            StreamUtils.copy(data, stream);
        } finally {
            IOUtils.closeQuietly(stream);
        }

        logger.info("Output spend {}", stopwatch.toString());
    }
}
