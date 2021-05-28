package rtx.stock.checker.action;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import lombok.AllArgsConstructor;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class StockChecking {
    public void sendEmail()  throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("2d61d533aeeb9abd979a5d8fc8c2e3e6","28edb86f184a1080ba25c366097522e9",
                new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "adnan.netboys@gmail.com")
                                        .put("Name", "Derek"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", "adnan.mohd.ahmed@gmail.com")
                                                .put("Name", "Adnan")))
                                .put(Emailv31.Message.SUBJECT, "RTX lay lo")
                                .put(Emailv31.Message.TEXTPART, "Lay lo")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear laylo https://www.krgkart.com/product/asus-nvidia-geforce-rtx-3070-8gb-dual-gddr6-graphics-card  </h3>")
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        response = client.post(request);
        log.info(String.valueOf(response.getStatus()));
        log.info(String.valueOf(response.getData()));
    }

    public void checkStock() throws Exception {
        String url = "https://www.krgkart.com/product/asus-nvidia-geforce-rtx-3070-8gb-dual-gddr6-graphics-card";
        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);
        HtmlElement itemAnchor = null;
        String itemName = "";
        try {
            itemAnchor = htmlPage.getFirstByXPath("/html/body/div[1]/div[2]/div/div/div/div/div[3]/div[2]/div[2]/div[1]/p");

        } catch (Exception e) {
            sendEmail();
            throw e;
        }
        try {
            itemName = itemAnchor.getVisibleText();
        } catch (NullPointerException e) {
            sendEmail();
            throw e;
        }
        log.info(itemName);
        if (itemName.isEmpty())
            sendEmail();
    }
}
