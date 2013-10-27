package jscover.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JasmineWebDriverRunner implements WebDriverRunner {

    @Override
    public void waitForTestsToComplete(WebDriver webClient) throws MojoExecutionException {
        try {
            new WebDriverWait(webClient, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("duration")));
            new WebDriverWait(webClient, 10).until(ExpectedConditions.textToBePresentInElement(By.className("duration"), "finished"));
        } catch(AssertionError e) {
            throw new MojoExecutionException("Problem waiting for tests to complete", e);
        }
    }

    @Override
    public void verifyTestsPassed(WebDriver webClient) throws MojoFailureException {
        if (webClient.findElements(By.className("failingAlert")).size() != 0) {
            new MojoFailureException("Failing on test");
        }
    }
}
