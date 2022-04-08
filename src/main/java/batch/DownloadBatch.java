package batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// 1주일에 한번씩 다운로드 해서 DB에 변경하기
// 전체 데이터를 가져오고 삭제 후 추가

@Component
public class DownloadBatch {
	
	// 초 분 시 일 월 주
	@Scheduled(cron= "0*****", zone= "Asia/Seoul")
 	public void startBatch() {
		System.out.println("나는 1분마다 실행됨");
	}
	
	
	public static void DownloadData() throws IOException {
		
	}
}
