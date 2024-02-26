import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PruebasPhantomjsIT
{
    private static WebDriver driver=null;

    @Test
    void tituloIndexTest()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
                String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(), "El titulo no es correcto");
        System.out.println(driver.getTitle());
        driver.close();
        driver.quit();
    }

    @Test
    void verVotosACeroTest()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
                String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto");
        driver.findElement(By.id("votos-a-cero-button")).click();
        driver.findElement(By.id("ver-votos-button")).click();
        driver.findElements(By.className("voto")).forEach(voto -> assertEquals("0", voto.getText().split("-")[1].trim()));
    }

    @Test
    void votarOtroTest() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
                String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto");
        driver.findElement(By.id("otro-input")).click();
        driver.findElement(By.name("txtOtros")).sendKeys("Pau Gasol");
        driver.findElement(By.name("B1")).click();
        driver.navigate().to("http://localhost:8080/Baloncesto");
        driver.findElement(By.id("ver-votos-button")).click();
        driver.findElements(By.className("voto")).forEach(voto -> {
            String[] votoSplit = voto.getText().split("-");
            if (votoSplit[0].contains("Pau Gasol")) {
                assertEquals("1", votoSplit[1].trim());
            }
        });
    }
}
