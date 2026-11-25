package org.example.utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class PreAndProsCondition {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreAndProsCondition.class);
    private int stepCounter = 1;

    @Before
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("_____________________________________________________");
        LOGGER.info("Running: " + scenario.getName());
        OnStage.setTheStage(new OnlineCast());
        stepCounter = 1;

        String sanitizedName = scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
        String folderPath = Parameters.RutaEvidencia + ScreenShot.getCurrentDateTime() + "_" + sanitizedName;
        Parameters.RutaEvidenciaActual = folderPath;
        ScreenShot.createFolder(folderPath);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!Parameters.RutaEvidenciaActual.isEmpty()) {
            ScreenShot.captureScreenshot(
                    getDriver(),
                    Parameters.RutaEvidenciaActual,
                    "paso_" + stepCounter++
            );
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        LOGGER.info("Finishing: " + scenario.getName());
        Parameters.RutaEvidenciaActual = "";
    }
}