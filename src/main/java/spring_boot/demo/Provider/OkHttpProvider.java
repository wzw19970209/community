package spring_boot.demo.Provider;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import spring_boot.demo.dto.AccessDTO;
import spring_boot.demo.dto.GitHubUser;

import java.io.IOException;

@Component
public class OkHttpProvider {
    public String getAccessToken(AccessDTO accessDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String substring = string.substring(string.indexOf("=")+1, string.indexOf("&"));
                return substring;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }
    public GitHubUser getGitHubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
             String string = response.body().string();
             GitHubUser gitHubUser = JSON.parseObject(string,GitHubUser.class);
             return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
