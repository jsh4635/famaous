package org.example;

import org.example.controller.FamousController;
import org.example.repository.entity.FamousSaying;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // 입력받기 위한 Scanner
    private static final Scanner IN = new Scanner(System.in);

    // 명언 컨트롤러 선언
    private static FamousController famousController = new FamousController();

    /**
     *
     * 메인 함수
     *
     * @param args
     */
    public static void main(String[] args) {
        // 마지막 ID 찾기 위한 함수
        //readLastId();
        // 저장된 명언 목록들 읽기 위한 함수
        //readFamousSayings();

        int lastId = famousController.readLastId() + 1;

        System.out.println("== 명언 앱 ==");

        // 명령어 받을 변수 선언
        String value = "";

        // 종료 명령어가 나올 때까지 하는 반복문
        while(!value.equals("종료")){
            // 명령어 입력받는 부분
            System.out.print("명령) ");
            value = IN.nextLine();

            if(value.equals("등록")){
                // 명언 등록 함수 호출
                lastId = famousController.addFamousSaying(lastId);
            }
            else if(value.contains("목록")){
                // 명언 목록 출력 함수 호출
                famousController.printFamousSayings(value);
            }
            else if(value.contains(("삭제?id="))){
                // 명언 삭제 함수 호출
                famousController.deleteFamousSaying(value);
            }
            else if(value.contains("수정?id=")){
                // 명언 수정 함수 호출
                famousController.updateFamousSaying(value);
            }
            else if(value.equals("빌드")){
                // data.json 파일 저장 함수 호출
                famousController.buildFamousSaying();
            }
        }
        IN.close();
    }
}