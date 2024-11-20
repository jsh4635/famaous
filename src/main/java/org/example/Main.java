package org.example;

import org.example.controller.FamousController;
import org.example.repository.entity.FamousSaying;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // 입력받기 위한 Scanner
    private static final Scanner IN = new Scanner(System.in);

    // 명언 목록을 넣기 위한 리스트
    //private static List<FamousSaying> famousSayings = new ArrayList<>();

    // 파일 저장 위치
    //private static final String FILE_PATH = "/Users/shjung/Desktop/Java/db/wiseSaying";

    // 명언 등록 아이디
    // - 처음 만들 때를 위한 1 초기화 선언
    //private static  int number = 1;

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
            else if(value.equals("목록")){
                // 명언 목록 출력 함수 호출
                famousController.printFamousSayings();
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
            else {
                // 특정 명령어가 아닐 경우 호출
                System.out.println("적절한 명령어가 아닙니다.");
            }
        }
        IN.close();
    }

    /**
     *
     * 마지막으로 저장된 ID 읽는 함수
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void readLastId(){
        // 마지막 ID 저장된 파일 불러오기
        File lastIdFile = new File(FILE_PATH + "/lastId.txt");
        // 만약에 없을 경우 함수 종료
        if(!lastIdFile.isFile()){
            return;
        }

        try {
            // 저장된 파일 읽어오기
            FileReader fileReader = new FileReader(lastIdFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 데이터 저장 변수
            String data = "";
            // 데이터 읽는 변수
            String line = "";
            // 모든 데이터 읽어서 데이터 저장 변수에 저장
            while((line = bufferedReader.readLine()) != null){
                data += line;
            }

            // 마지막 ID 에 저장
            // - +1 한 이유는 등록은 마지막 보다 하나 더 높아야하기 때문에
            number = Integer.parseInt(data) + 1;

            // 파일 읽기 종료
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     *
     * 명언 읽고 목록에 저장하는 함수
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void readFamousSayings(){
        // 파일이 저장된 위치
        File directory = new File(FILE_PATH);

        // 디렉토리가 존재하지 않을 경우 함수 종료
        if(!directory.exists()){
            return;
        }

        try {
            // 파일 목록 필터링
            // - json 파일만 불러오기 위함
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith("json");
                }
            };
            // 파일 목록 저장
            File[] files = directory.listFiles(fileFilter);

            if(files != null){

                // 파일 목록에서 이름만 저장
                List<String> filenames = Arrays.stream(files).map(f -> f.getName()).toList();

                filenames = filenames.stream().filter(f -> !f.equals("data.json")).toList();

                String[] filenames1 = filenames.toArray(new String[filenames.size()]);

                Arrays.sort(filenames1, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        String s1 = o1.replaceAll("[^0-9]", "");
                        String s2 = o2.replaceAll("[^0-9]", "");
                        int a = Integer.valueOf(s1);
                        int b = Integer.valueOf(s2);
                        return a - b;
                    }
                });

                filenames = Arrays.stream(filenames1).toList();

                for(String filename : filenames){
                    // 명언 파일 읽기
                    // - 파일 경로 + /파일 이름
                    File newFile = new File(FILE_PATH + "/" + filename);
                    FileReader fileReader = new FileReader(newFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String data = "";
                    String line = "";
                    while((line = bufferedReader.readLine()) != null){
                        data += line;
                    }

                    // 파일 데이터 분류
                    // - 전부 다 String 값으로 불러오기 때문에
                    String[] datas = data.split(",");

                    // 명언 데이터 저장할 오브젝트 생성
                    FamousSaying famousSaying = new FamousSaying();

                    // ID 저장
                    int id = Integer.parseInt(datas[0].replaceAll("[^0-9]", ""));
                    // 명언 저장
                    String contents = datas[1].split("\"")[3];
                    // 작가 저장
                    String author = datas[2].split("\"")[3];

                    // 명언 오브젝트에 데이터 저장
                    famousSaying.setId(id);
                    famousSaying.setContents(contents);
                    famousSaying.setAuthor(author);

                    // 명언 목록에 명언 오브젝트 추가
                    famousSayings.addFirst(famousSaying);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }*/

    /**
     *
     * 명언 등록 함수
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void addFamousSaying(){
        // 명언 데이터 입력
        System.out.print("명언: ");
        String contents = IN.nextLine();
        System.out.print("작가: ");
        String author = IN.nextLine();
        // 명언 오브젝트에 저장 및 목록에 추가
        FamousSaying famousSaying = new FamousSaying(number, contents, author);
        famousSayings.addFirst(famousSaying);

        // 명언 파일 생성 및 마지막으로 생성된 ID 파일 생성
        File dataFile = new File(FILE_PATH + "/" + number + ".json");
        File lastId = new File(FILE_PATH + "/lastId.txt");
        // 파일 생성 및 저장
        try {
            // 명언 파일 생성 및 저장
            FileWriter fileWriter = new FileWriter(dataFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(famousSaying.toString());

            // 마지막 생성된 ID 생성 및 저장
            FileWriter lastIdWriter = new FileWriter(lastId);
            BufferedWriter bufferedIdWriter = new BufferedWriter(lastIdWriter);

            bufferedIdWriter.write(String.valueOf(number));

            // 파일 생성 스트림 종료
            bufferedWriter.close();
            fileWriter.close();

            bufferedIdWriter.close();
            lastIdWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 몇번 명언이 등록되었는지 확인
        System.out.println(number + "번 명언이 등록되었습니다.");
        // ID 증가
        number++;
    }*/

    /**
     *
     * 목록 출력 함수
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void printFamousSayings(){
        // 목록 출력
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");
        // for문을 이용한 목록 출력
        for(FamousSaying f : famousSayings){
            System.out.println(f.getId() + " / " + f.getAuthor() + " / " + f.getContents());
        }
    }*/

    /**
     *
     * 명언 삭제 함수
     *
     * @param value 명령어
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void deleteFamousSaying(String value){
        // 명령어에서 ID 추출
        value = value.replaceAll("[^0-9]", "");
        int index = Integer.parseInt(value);

        // 삭제할 파일 위치 찾기
        File dataFile = new File(FILE_PATH + "/" + index + ".json");

        try{
            // 목록에서 파일 삭제
            FamousSaying newFamousSaying = famousSayings.stream().filter(f -> f.getId() == index).findFirst().orElseThrow();
            famousSayings = famousSayings.stream().filter(f -> f.getId() != index).collect(Collectors.toList());

            // 파일이 존재할 경우 파일 삭제
            if(dataFile.isFile()){
                dataFile.delete();
            }

            System.out.println(index + "번 명언이 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println(index + "번 명언이 존재하지 않습니다.");
        }
    }*/

    /**
     *
     * 명언 수정 함수
     *
     * @param value 명령어
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void updateFamousSaying(String value){
        // 명령어에서 명언 ID 추출
        value = value.replaceAll("[^0-9]", "");
        int index = Integer.parseInt(value);

        // 수정할 파일 찾기
        File dataFile = new File(FILE_PATH + "/" + index + ".json");
        // 새로운 명언 오브젝트 생성
        FamousSaying newFamousSaying = new FamousSaying();
        try{
            // 새로운 명언에 기존 ID를 가진 명언 저장 데이터 저장
            newFamousSaying = famousSayings.stream().filter(f -> f.getId() == index).findFirst().orElseThrow();
            // 명언 데이터 수정
            System.out.println("명언(기존): " + newFamousSaying.getContents());
            System.out.print("명언: ");
            String contents = IN.nextLine();
            System.out.println("작가(기존): " + newFamousSaying.getAuthor());
            System.out.print("작가: ");
            String author = IN.nextLine();

            // 명언 목록에 데이터 수정
            famousSayings.stream().filter(f -> f.getId() == index).forEach(f -> {
                f.setContents(contents);
                f.setAuthor(author);
            });

            newFamousSaying.setAuthor(author);
            newFamousSaying.setContents(contents);

            // 명언 파일 수정
            if(dataFile.isFile()){
                FileWriter fileWriter = new FileWriter(dataFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(newFamousSaying.toString());

                bufferedWriter.close();
                fileWriter.close();
            }

        }
        catch (Exception e){
            System.out.println(index + "번 명언이 존재하지 않습니다.");
        }

    }*/

    /**
     *
     * 명언 파일 빌드 함수
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    /*private static void buildFamousSaying(){
        // 명언 데이터 파일 위치 찾기
        File dataFile = new File(FILE_PATH + "/data.json");
        try {
            // 명언 데이터 파일에 데이터 저장 및 갱신
            FileWriter fileWriter = new FileWriter(dataFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(famousSayings.toString());

            System.out.println("data.json 파일의 내용이 갱신되었습니다.");

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}