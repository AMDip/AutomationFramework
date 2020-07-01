package testpackage;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        plugin = {"de.monochromata.cucumber.report.PrettyReports:target/cucumber",  // plugin for cucumber reports
                "pretty", "json:target/cucumber-reports/Cucumber.json"
                //"com.mesaj.app.util.reporting.JReporter",
                //"junit:target/cucumber-reports/Cucumber.xml",
                //"html:target/cucumber-reports"
        })
public class TestRunner {
}
