package de.adesso.maitredecuisine.imagerecognition;

import de.adesso.maitredecuisine.MaitreDeCuisineApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { MaitreDeCuisineApplication.class })
public class GraphModelTest {

    @Autowired
    private GraphModel graphModel;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(graphModel,"loadModel",false);
    }

    /**
     * Existiert die Model Datei?
     * Sie ist zu groß für Github und muss deshalb getrennt bereitgestellt werden.
     * Die ZipDatei aus T:\maitredecuisine muss in MatreDeCuisine\src\main\resources\model\oidv2 ausgepackt werden.
     * @throws IOException
     */
    @Test
    public void testModelExists() throws IOException {
        String fileName = graphModel.modelFileName();
        File f = new File(fileName);
        assertTrue("Das Tensorflow Model wurde nicht gefunden. Es muss außerhalb von github hinzugrfügt werden.",
                f.exists());
    }

    /**
     * Kann die Modeldatei von Tensorflow geladen werden?
     * @throws IOException
     */
    @Test
    public void testModelLoads() throws IOException {
        ReflectionTestUtils.setField(graphModel,"loadModel",true);
        graphModel.loadGraph();
        assertNotNull(graphModel.getGraph());
    }

}