package demo.wrappers;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {

    public static void enterTextWrapper(WebDriver driver, By locator, String text) {
        System.out.println("Sending text in search box");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement inputBox = driver.findElement(locator);
            inputBox.clear();
            inputBox.sendKeys(text);
            inputBox.sendKeys(Keys.ENTER);
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured!" + e.getMessage());
            success = false;
        }
    }

    public static void clickOnElementWapper(WebDriver driver, By locator) {
        System.out.println("Clicking on Element");

        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement clickableElement = driver.findElement(locator);
            clickableElement.click();
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured!" + e.getMessage());
            success = false;
        }
    }

    public static Boolean seachStarRatingAndPrintCount(WebDriver driver, By locator, double starRating) {
        int washingMachineCount = 0;
        Boolean success;

        try {
            List<WebElement> starRatingElements = driver.findElements(locator);
            for (WebElement starRatinElement : starRatingElements) {
                if (Double.parseDouble(starRatinElement.getText()) <= starRating) {

                    washingMachineCount++;
                }
            }
            System.out.println("Count of washing machine which has star rating less than or equal to"
                    + ": " + washingMachineCount);
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }


   public static Boolean printTitleAndDiscountIphone(WebDriver driver, By locator, int discount) {
    Boolean success;
    try {
        List<WebElement> productRows = driver.findElements(locator);
        HashMap<String, String> iphoneTitleDiscountMap = new HashMap<>();
        for (WebElement productRow : productRows) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                // Wait for the discount element to be visible
                WebElement discountElement = wait.until(ExpectedConditions.visibilityOf(
                    productRow.findElement(By.xpath(".//div[@class='UkUFwK']//span[contains(text(),'off')]"))));
                String discountPercent = discountElement.getText();
                int discountValue = Integer.parseInt(discountPercent.replaceAll("[^\\d]", ""));
                if (discountValue > discount) {
                    // Wait for the title element to be visible
                    WebElement titleElement = wait.until(ExpectedConditions.visibilityOf(
                        productRow.findElement(By.xpath(".//div[@class='KzDlHZ']"))));
                    String iphoneTitle = titleElement.getText();
                    iphoneTitleDiscountMap.put(discountPercent, iphoneTitle);
                }
            } catch (NoSuchElementException e) {
                // Handle missing elements gracefully
                System.out.println("Discount or title element not found for this product.");
            } catch (TimeoutException e) {
                // Handle timeout when element is not found within the wait time
                System.out.println("Timeout waiting for discount or title element.");
            }
        }
        // Printing the results
        for (Map.Entry<String, String> iphoneTitleDiscounts : iphoneTitleDiscountMap.entrySet()) {
            System.out.println("iPhone discount percentage is :: " + iphoneTitleDiscounts.getKey() 
                + " and its title is :: " + iphoneTitleDiscounts.getValue());
        }
        success = true;
    } catch (Exception e) {
        System.out.println("Exception Occurred!");
        e.printStackTrace();
        success = false;
    }
    return success;
}
    public static Boolean printTitleAndImageUrlOfCoffeeMug(WebDriver driver, By locator) {
        Boolean success;
        try {
            List<WebElement> userReviewElements = driver.findElements(locator);
            Set<Integer> userReviewSet = new HashSet<>();
            for (WebElement userReviewElement : userReviewElements) {
                int userReview = Integer.parseInt(userReviewElement.getText().replaceAll("[^\\d]", ""));
                userReviewSet.add(userReview);
            }
            List<Integer> userReviewCountList = new ArrayList<>(userReviewSet);
            Collections.sort(userReviewCountList, Collections.reverseOrder());
            System.out.println(userReviewCountList);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            LinkedHashMap<String, String> productDetailsMap = new LinkedHashMap<>();

            for (int i = 0; i < 5; i++) {
                String formattedUserReviewCount = "(" + numberFormat.format(userReviewCountList.get(i)) + ")";
                String productTitle = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"
                        + formattedUserReviewCount + "')]/../../a[@class='wjcEIp']")).getText();
                String productImgURL = driver.findElement(By.xpath("//div[@class='slAVV4']//span [contains(text(),'"
                        + formattedUserReviewCount + "')]/../..//img[@class='DByuf4']")).getAttribute("src");

                String hightestReviewCountAndProductTitle = String.valueOf(i + 1) + " highest review count is : " + formattedUserReviewCount + " and Title is :";
                productDetailsMap.put(hightestReviewCountAndProductTitle, productImgURL);
            }
//print title and image url of coffee mug
            for (Map.Entry<String, String> productDetails : productDetailsMap.entrySet()) {
                System.out.println(productDetails.getKey() + " and Product image url is : " + productDetails.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

}
