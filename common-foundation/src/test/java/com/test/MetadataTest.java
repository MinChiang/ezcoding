package com.test;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.LayerModuleFactory;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.metadata.Metadata;
import com.ezcoding.common.foundation.core.metadata.SerializationTypeEnum;
import com.ezcoding.common.foundation.core.metadata.impl.FunctionLayerModuleMetadataFetcher;
import com.ezcoding.common.foundation.core.metadata.impl.PropertiesFileFetcher;
import com.ezcoding.common.foundation.core.metadata.impl.ScheduleMetadataManager;
import com.ezcoding.common.foundation.core.metadata.impl.SimpleMetadataFetcher;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 19:56
 */
public class MetadataTest {

    private static final String BUCKET = "test";
    private static final String JOINER = "-";
    private static final String BUCKET_JOINER = BUCKET + JOINER;

    private static final String STRING = "STRING";
    private static final String CHARACTER = "CHARACTER";
    private static final String BOOLEAN = "BOOLEAN";
    private static final String BYTE = "BYTE";
    private static final String SHORT = "SHORT";
    private static final String INTEGER = "INTEGER";
    private static final String LONG = "LONG";
    private static final String FLOAT = "FLOAT";
    private static final String DOUBLE = "DOUBLE";

    private static final String DEFAULT_STRING = "something";
    private static final Character DEFAULT_CHARACTER = 'c';
    private static final Boolean DEFAULT_BOOLEAN = true;
    private static final Byte DEFAULT_BYTE = 0X11;
    private static final Short DEFAULT_SHORT = 15;
    private static final Integer DEFAULT_INTEGER = 77;
    private static final Long DEFAULT_LONG = 229249155622L;
    private static final Float DEFAULT_FLOAT = 234.2F;
    private static final Double DEFAULT_DOUBLE = 22667.214D;

    private static final ApplicationLayerModule APPLICATION_LAYER_MODULE = LayerModuleFactory.applicationLayerModule("testApplication", 0);
    private static final ModuleLayerModule MODULE_LAYER_MODULE = LayerModuleFactory.moduleLayerModule(APPLICATION_LAYER_MODULE, "testModule", 0);
    private static final FunctionLayerModule FUNCTION_LAYER_MODULE = LayerModuleFactory.functionLayerModule(MODULE_LAYER_MODULE, "testFunction", 0);

    private PropertiesFileFetcher propertiesFileFetcher;
    private SimpleMetadataFetcher simpleMetadataFetcher;

    private ScheduleMetadataManager scheduleMetadataManager;
    private FunctionLayerModuleMetadataFetcher functionLayerModuleMetadataFetcher;

    @Before
    public void init() {
        this.propertiesFileFetcher = new PropertiesFileFetcher(0, JOINER, Collections.singletonList(new File("D:\\personal\\workspace\\ezcoding_distribution\\common-foundation\\src\\test\\resources\\properties\\test.properties")));
        this.simpleMetadataFetcher = new SimpleMetadataFetcher(new HashMap<String, Metadata>() {{
            for (int i = 0; i < 5; i++) {
                String key = "key" + i;
                Metadata metadata = new Metadata(JOINER, BUCKET, key, SerializationTypeEnum.STRING, key);
                put(metadata.getIdentification(), metadata);
            }
        }});

        this.scheduleMetadataManager = new ScheduleMetadataManager(10_000);
        this.scheduleMetadataManager.register(this.propertiesFileFetcher);
        this.scheduleMetadataManager.register(this.simpleMetadataFetcher);
        this.scheduleMetadataManager.load();

        functionLayerModuleMetadataFetcher = new FunctionLayerModuleMetadataFetcher(this.scheduleMetadataManager);
    }

    @Test
    public void testFetch() {
        Map<String, Metadata> propertiesMetadata = propertiesFileFetcher.fetch();
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "string").getContent(), "top");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "character").getContent(), "a");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "boolean").getContent(), "false");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "byte").getContent(), "42");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "integer").getContent(), "12");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "long").getContent(), "24212314");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "float").getContent(), "223.45");
        assertEquals(propertiesMetadata.get(BUCKET_JOINER + "double").getContent(), "9948.2");

        Metadata metadata = propertiesMetadata.get(BUCKET_JOINER + "key0");
        assertEquals(metadata.getType(), SerializationTypeEnum.STRING);
        assertEquals(metadata.getContent(), "xixi");
        assertEquals(metadata.getCreateTime(), new Date(1624873128000L));
        assertEquals(metadata.getDescription(), "这个是xixi描述");

        Map<String, Metadata> simpleMetadata = simpleMetadataFetcher.fetch();
        for (Map.Entry<String, Metadata> entry : simpleMetadata.entrySet()) {
            String key = entry.getKey();
            Metadata value = entry.getValue();
            assertEquals(key, BUCKET_JOINER + value.getContent());
        }
    }

    @Test
    public void convertTest() throws InterruptedException {
    }

}
