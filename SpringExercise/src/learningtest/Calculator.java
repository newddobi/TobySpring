package learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	public Integer calcSum(String filepath) throws IOException{
		LineCallback sumCallback =
			new LineCallback() {
				public Integer doSomethingWithLine(String line, Integer value) {
					return value + Integer.valueOf(line);
				}
			};
		return lineReadTemplate(filepath, sumCallback, 0);
	}
	
	public Integer calcMultiply(String filepath) throws IOException {
		LineCallback multiplyCallback =
			new LineCallback() {
				public Integer doSomethingWithLine(String line, Integer value) {
					return value * Integer.valueOf(line);
				}
			};
		return lineReadTemplate(filepath, multiplyCallback, 1);
	}
	
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			//콜백 오브젝트 호출. 템플릿에서 만든 컨텍스트 BufferedReader를 전달해주고 콜백의 작업 결과를 받아둔다.
			int ret = callback.doSomethingWithReader(br);
			return ret;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try { br.close(); } 
				catch (IOException e) { System.out.println(e.getMessage()); }
			}
		}
	}
	
	public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			Integer res = initVal;
			String line = null;
			//파일의 각 라인을 루프를 돌면서 가져 오는 것도 템플릿이 담당한다.
			while ((line = br.readLine()) != null) {
				//콜백이 계산한 값을 저장해뒀다가 다음 라인 계산에 다시 적용한다.
				res = callback.doSomethingWithLine(line, res); //각 라인이 내용을 가지고 계산하는 작업만 콜백에게 맡긴다.
			}
			return res;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try { br.close(); } 
				catch (IOException e) { System.out.println(e.getMessage()); }
			}
		}
	}
}
