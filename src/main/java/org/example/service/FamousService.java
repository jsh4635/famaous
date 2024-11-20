package org.example.service;

import org.example.controller.model.FamousSayingModel;
import org.example.repository.FamousRepository;
import org.example.repository.entity.FamousSaying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// 데이터 호출, 삭제 및 데이터 가공
public class FamousService {

    private static final FamousRepository famousRepository = new FamousRepository();

    public String addFamousSaying(int id, String contents, String author){
        String message = "";

        FamousSaying famousSaying = new FamousSaying(id, contents, author);
        int check = famousRepository.createFile(famousSaying.getId() + ".json", famousSaying, true);
        int lastIdCheck = famousRepository.createFile("lastId.txt", famousSaying, false);

        if(check == 0 || lastIdCheck == 0){
            message = id + "번 명언 등록에 실패하였습니다.";
        } else {
            message = id + "번 명언이 등록되었습니다.";
        }

        return message;
    }

    public List<FamousSayingModel> readFamousSayings(){
        List<FamousSayingModel> famousSayingModels = new ArrayList<>();

        List<String> filenames = famousRepository.findFilenames();

        filenames = filenames.stream().filter(f -> !f.equals("data.json")).toList();

        String[] filenames1 = filenames.toArray(new String[filenames.size()]);

        Arrays.sort(filenames1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1.replaceAll("[^0-9]", "");
                String s2 = o2.replaceAll("[^0-9]", "");
                int a = Integer.parseInt(s1);
                int b = Integer.parseInt(s2);
                return a - b;
            }
        });

        filenames = Arrays.stream(filenames1).toList();

        for(String filename : filenames){
            String data = famousRepository.readFamousSaying(filename);

            // 파일 데이터 분류
            // - 전부 다 String 값으로 불러오기 때문에
            String[] datas = data.split(",");

            // 명언 데이터 저장할 오브젝트 생성
            FamousSayingModel famousSaying = new FamousSayingModel();

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
            famousSayingModels.addFirst(famousSaying);
        }

        return famousSayingModels;
    }

    public FamousSayingModel readFamousSaying(int id){
        String data = famousRepository.readFamousSaying(id + ".json");
        // 파일 데이터 분류
        // - 전부 다 String 값으로 불러오기 때문에
        String[] datas = data.split(",");

        // 명언 데이터 저장할 오브젝트 생성
        FamousSayingModel famousSaying = new FamousSayingModel();

        // ID 저장
        id = Integer.parseInt(datas[0].replaceAll("[^0-9]", ""));
        // 명언 저장
        String contents = datas[1].split("\"")[3];
        // 작가 저장
        String author = datas[2].split("\"")[3];

        // 명언 오브젝트에 데이터 저장
        famousSaying.setId(id);
        famousSaying.setContents(contents);
        famousSaying.setAuthor(author);

        return famousSaying;
    }

    public String updateFamousSaying(FamousSayingModel famousSayingModel){
        String message = "";

        FamousSaying famousSaying = new FamousSaying(famousSayingModel.getId(), famousSayingModel.getContents(), famousSayingModel.getAuthor());

        int check = famousRepository.createFile(famousSaying.getId() + ".json", famousSaying, true);

        if(check == 0) message = famousSaying.getId() + "번 명언이 존재하지 않습니다..";
        else if(check == 1) message = famousSaying.getId() + "번 명언이 수정되었습니다.";

        return message;
    }

    public String deleteFamousSaying(int id){
        String message = "";

        String filename = id + ".json";

        int check = famousRepository.deleteFamousSaying(filename);

        if(check == 0) message = id + "번 명언이 존재하지 않습니다.";
        else if(check == 1) message = id + "번 명언을 삭제하였습니다..";

        return message;
    }

    public int readLastId(){
        int lastId = famousRepository.readLastId();
        return lastId;
    }

    public String createDataJson(List<FamousSayingModel> famousSayingModels){
        String message = "";
        List<FamousSaying> famousSayings = new ArrayList<>();

        famousSayingModels.stream().forEach(f -> {
            FamousSaying famousSaying = new FamousSaying(f.getId(), f.getContents(), f.getAuthor());
            famousSayings.add(famousSaying);
        });

        int check = famousRepository.createDataJson(famousSayings);

        if(check == 0) message = "data.json 갱신에 실패하였습니다.";
        else if(check == 1) message = "data.json 파일의 내용이 갱신되었습니다.";

        return message;
    }
}
