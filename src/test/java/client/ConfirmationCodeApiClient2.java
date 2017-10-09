package client;

import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ConfirmationCodeApiClient2 {

    private static final String PATH_CRON_SEND_OUTGOING_MESSAGES = "/cron/sendOutgoingMessages";
    private static final String PATH_API_OUTGOING_MESSAGE_LOG = "/api/OutgoingMessageLog";
    private static final String PATH_API_OUTGOING_MESSAGE = "/api/OutgoingMessage";
    private static final String PARAM_TO = "to";
    private static final String PARAM_TEMPLATE = "template";
    private static final String CONFIRMATION_CODE_TEMPLATE = "110";
    private static final String ONE_TIME_PIN_TEMPLATE = "112";
    private static final String ID = "id";
    private static final String MESSAGE = "message";
    private static final String serverApiBaseUrl = "https://qaserverapi.inventureaccess.com";
    private static final int TIMEOUT_CONNECTION = 90; // timeout in seconds
    private static final int TIMEOUT_SOCKET = 90; // timeout in seconds
    protected static final String SPACE_DELIMITER = " ";
    protected static final String DATA_TAG = "data";
    protected static final String EMPTY_STRING = "";
    private static final String OPEN_RESULT_TAG = "<result>";
    private static final String CLOSE_RESULT_TAG = "</result>";
    protected static final String ERRORS_TAG = "errors";

    private HttpUrl sendOutgoingMessagesApiUrl = HttpUrl.parse(serverApiBaseUrl + PATH_CRON_SEND_OUTGOING_MESSAGES);
    private HttpUrl outgoingMessageLogApiUrl = HttpUrl.parse(serverApiBaseUrl + PATH_API_OUTGOING_MESSAGE_LOG);
    private HttpUrl outgoingMessageApiUrl = HttpUrl.parse(serverApiBaseUrl + PATH_API_OUTGOING_MESSAGE);

    /**
     * Returns json {@link String} from {@link Response}.
     *
     * @param response the response
     * @throws IOException
     */
    protected String extractJsonString(final Response response) throws IOException {
        String jsonString = StringUtils.replace(response.body().string(), OPEN_RESULT_TAG, EMPTY_STRING);
        response.body().close();
        return StringUtils.replace(jsonString, CLOSE_RESULT_TAG, EMPTY_STRING);
    }

    /**
     * Returns {@link JSONObject} from {@link Response}.
     *
     * @param response the response
     * @throws IOException
     */
    protected JSONObject extractJsonObject(final Response response) throws IOException, ApiClientException{
        JSONObject jsonObject = new JSONObject(extractJsonString(response));
        if (hasErrors(jsonObject)) {
            throw new ApiClientException(flattenErrors(jsonObject));
        }
        return jsonObject;
    }
    /**
     * Returns a string of errors.
     *
     * @param jsonObject the json object.
     */
    protected String flattenErrors(final JSONObject jsonObject) {
        return jsonObject.getJSONArray(ERRORS_TAG).toString();
    }
    /**
     * Returns true if json object has errors, otherwise false.
     *
     * @param jsonObject the json object
     */
    protected boolean hasErrors(final JSONObject jsonObject) {
        try {
            return jsonObject.getJSONArray(ERRORS_TAG).length() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns confirmation code given a phone number.
     *
     * @param phoneNumber
     */
    public String retrieveConfirmationCode(final String phoneNumber) throws IOException, ApiClientException {
        // send outgoing messages
        executeRequest(getRequest(sendOutgoingMessagesApiUrl));

        // retrieve outgoing message log
        HttpUrl httpUrl = outgoingMessageApiUrl.newBuilder()
                .addQueryParameter(PARAM_TO, phoneNumber)
                .addQueryParameter(PARAM_TEMPLATE, CONFIRMATION_CODE_TEMPLATE)
                .build();
        Response response = executeRequest(getRequest(httpUrl));
        JSONArray jsonArray = extractJsonObject(response).getJSONArray(DATA_TAG);
        Iterator iterator = jsonArray.iterator();
        int id = -1;
        String message = null;
        while (iterator.hasNext()) {
            JSONObject item = new JSONObject(iterator.next().toString());
            if (id < item.getInt(ID)) {
                id = item.getInt(ID);
                message = item.getString(MESSAGE);
                System.out.println("The confirmation code is "+ message);
            }
        }
        return StringUtils.split(message, SPACE_DELIMITER)[0];
    }

    /**
     * Executes a {@link Request} and returns a {@link Response} object.
     *
     * @param request the request
     * @throws IOException
     */
    protected Response executeRequest(final Request request) throws IOException {
        return okHttpClient.newCall(request)
                .execute();
    }
    /**
     * Returns get {@link Request}.
     *
     * @param httpUrl the http url
     */
    protected Request getRequest(final HttpUrl httpUrl) {
        return getRequest(httpUrl, null);
    }

    /**
     * Returns get {@link Request}.
     *
     * @param httpUrl the http url
     * @param headers the headers
     */
    protected Request getRequest(final HttpUrl httpUrl, final Headers headers) {
        Request.Builder builder = new Request.Builder()
                .url(httpUrl)
                .get();
        if (null != headers) {
            builder.headers(headers);
        }
        return builder.build();
    }

    /**
     * Http client and urls.
     */
    protected OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .build();
}
