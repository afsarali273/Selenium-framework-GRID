package qa.hs.framework;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Reporter;

public class SeUtil {
	
	private WebDriver driver;
	
	public SeUtil( WebDriver driver ) {
		this.driver = driver;
	}

	//TODO This method does not work, using the driver instance in this class.  Why?
	public WebElement getElementByLocator( By locator ) {
		Reporter.log( "Get element by locator: " + locator.toString(), true );                
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
			    .withTimeout( 30, TimeUnit.SECONDS )
			    .pollingEvery( 5, TimeUnit.SECONDS )
			    .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
		WebElement we = wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
		Reporter.log( "Finished getting element: " + locator, true );
		return we;
	}
	
	public WebElement getElementByLocator( WebDriver drv, By locator ) {
		Reporter.log( "Get element by locator: " + locator.toString(), true );                
		Wait<WebDriver> wait = new FluentWait<WebDriver>( drv )
			    .withTimeout( 30, TimeUnit.SECONDS )
			    .pollingEvery( 5, TimeUnit.SECONDS )
			    .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
		WebElement we = wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
		Reporter.log( "Finished getting element: " + locator, true );
		return we;
	}
	
	public void waitTimer( int units, int mills ) {
		DecimalFormat df = new DecimalFormat("###.##");
		double totalSeconds = ((double)units*mills)/1000;
		Reporter.log( "Explicit pause for " + df.format(totalSeconds) + " seconds divided by " + units + " units of time: ", true );
		try {
			Thread.currentThread();                
			int x = 0;
			while( x < units ) {
				Thread.sleep( mills );
				x = x + 1;
			}
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}        
	}

	public boolean elementsExist( By locator ) {
		waitTimer( 1, 500 );
		return driver.findElements( locator ).size() != 0;
	}

}
