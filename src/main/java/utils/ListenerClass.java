package utils;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import base.Base;
import reports.ExtentManager;


public class ListenerClass extends ExtentManager implements ITestListener {


	
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getName());
		
	}

	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Pass Test case is: " + result.getName());
		}
	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			test.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			String imgPath = screenShot(Base.page, result.getName());

			test.fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
		}
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
		}
	}
	
	//Method for capturing Screenshots
	public String screenShot(Page page, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String newImageString = System.getProperty("user.dir") + "/ScreenShots/" + filename + "_" + dateName + ".png";
		page.screenshot(new Page.ScreenshotOptions()
			      .setPath(Paths.get(newImageString)));
		return newImageString;
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
		}
	

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}
}
