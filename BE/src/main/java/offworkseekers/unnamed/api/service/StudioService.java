package offworkseekers.unnamed.api.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import offworkseekers.unnamed.api.request.StudioCreateRequest;
import offworkseekers.unnamed.api.response.*;
import offworkseekers.unnamed.db.entity.*;
import offworkseekers.unnamed.db.repository.StoryRepository;
import offworkseekers.unnamed.db.repository.StudioRepository;
import offworkseekers.unnamed.db.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StudioService {

    private final StudioRepository studioRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    public StudioNavBarResponse getStudioNavbar(Long studioId, String userId){
        return studioRepository.findStudioNavbar(studioId, userId);
    }

    @Transactional
    public void saveStudio(StudioCreateRequest request) {
        Story story = storyRepository.findById(request.getStoryId()).orElse(null);
        Studio studio = request.toStudioEntity();
        studio.connectStory(story);

        List<User> userList = new ArrayList<>();
        List<String> teamMembersIds = request.getTeamMembersIds();
        for (String teamMembersId : teamMembersIds) {
            User user = userRepository.findById(teamMembersId).orElse(null);
            userList.add(user);
        }
        studio.addTeamMember(userList);
        studioRepository.save(studio);
    }

    public List<UserSearchResponse> searchUser(String keyword) {
        return userRepository.findUserSimple(keyword);
    }

    public String getStudioStoryVideoUrl(Long studioId) {
        Studio studio = studioRepository.findById(studioId).orElse(null);
        Story story = storyRepository.findById(studio.getStory().getStoryId()).orElse(null);

        return story.getStoryVideoUrl();
    }

    public StudioSettingResponse getStudioSetting(Long studioId, String userId) {
        StudioSettingResponse studioSetting = studioRepository.findStudioSetting(studioId, userId);
        return studioSetting;
    }

    public List<StudioFilmListResponse> getStudioFilmList(Long studioId) {
        List<Film> studioFilmList = studioRepository.getStudioFilmList(studioId);

        List<StudioFilmListResponse> responses = new ArrayList<>();

        for (Film film : studioFilmList) {
            responses.add(
                    StudioFilmListResponse.builder()
                            .filmVideoUrl(film.getFilmVideoUrl())
                            .filmCreatedDate(film.getFilmCreatedDate())
                            .filmEndDate(film.getFilmCreatedDate().plusDays(7))
                            .build()
            );
        }
        return responses;
    }

    public List<StudioRecordListResponse> getStoryRecordingList(Long studioId) {
        return studioRepository.findRecordingByStudioId(studioId);
    }

    public String createFilm(String filePath) throws IOException{

        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .addInput(filePath + ".txt")
                .addExtraArgs("-f", "concat")
                .addExtraArgs("-safe", "0")
                .addOutput(filePath + ".mp4")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        return filePath+".mp4";
    }

    public String createMergeTxt(int studioId, int storyId) throws Exception {

//        String[] command = new String[]{"ffmpeg", "-i", "/mnt/s3/test1.mp4", "-i", "/mnt/s3/test2.mp4", "-c", "copy", "/mnt/s3/output.mp4"};
//        String[] command = new String[]{"ffmpeg", "-f", "concat", "-safe", "0", "-i", "/mnt/s3/merge.txt", "-c", "copy", "/mnt/s3/output.mp4"};
//        ProcessBuilder builder = new ProcessBuilder(command);
//        Process process = builder.start();
//        process.waitFor();

        Regions clientRegion = Regions.AP_NORTHEAST_2;
        final String accessKey = "AKIAQUIHCELPJ3PJXH55";
        final String secretKey = "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";

// S3 client
        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(clientRegion)
                .build();

        String bucketName = "s3ffmpegtest";

// top level folders and files in the bucket
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withDelimiter("/")
                    .withMaxKeys(300);

            ObjectListing objectListing = s3.listObjects(listObjectsRequest);

//            System.out.println("Folder List:");
//            for (String commonPrefixes : objectListing.getCommonPrefixes()) {
//                System.out.println("    name=" + commonPrefixes);
//            }

            List<String> cmd = new ArrayList<>();
            cmd.add("echo");
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                cmd.add("file '"+objectSummary.getKey()+"'");
                cmd.add("\n");
            }
            cmd.add("merge.txt");
            ProcessBuilder builder = new ProcessBuilder(cmd);
            Process process = builder.start();
            process.waitFor();
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
//
//        // 파일 리스트
//        File dir = new File("/mnt/s3");
//        String[] filenames = dir.list();
//
//        // merge.txt 생성할 위치
//        String filePath = "mnt/s3/merge_" + studioId + "_" + storyId;
//        String mergeTxtPath = filePath + ".txt";
//
//        File mergeTxt = new File(mergeTxtPath); // File객체 생성
//        System.out.println(mergeTxtPath);
//        if(!mergeTxt.exists()){ // 파일이 존재하지 않으면
//            mergeTxt.createNewFile(); // 신규생성
//        }
//
//        // BufferedWriter 생성
//        BufferedWriter writer = new BufferedWriter(new FileWriter(mergeTxt, true));
//
//        for (String filename : filenames) {
//            writer.write("file '" + filename + "'");
//            writer.newLine();
//        }
//
//        // 버퍼 및 스트림 뒷정리
//        writer.flush(); // 버퍼의 남은 데이터를 모두 쓰기
//        writer.close(); // 스트림 종료

        return null;
    }

    public void copyS3() throws Exception{

        String echo = "/usr/bin/s3fs s3ffmpegtest /mnt/s3 -o allow_other";
        List<String> cmd = new ArrayList<>();
        cmd.add("bash");
        cmd.add("/home/ubuntu/s3mount.sh");
        System.out.println(cmd);
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(new File("/home/ubuntu"));
        Process process = pb.start();
        process.waitFor();

//        ProcessBuilder builder = new ProcessBuilder();
//        builder.command("bash", "-c", "/usr/bin/s3fs s3ffmpeg /mnt/s3 -o allow_other");
//        Process process = builder.start();
//        process.waitFor();
        System.out.println("s3 마운트===========================");

//        String[] copyS3toLocal = new String[]{"sudo", "aws", "s3", "cp", "s3://s3ffmpegtest", "/mnt/s3ffmpegtest", "--recursive"};
//        ProcessBuilder builder = new ProcessBuilder(copyS3toLocal);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("bash", "-c", "/usr/bin/aws s3 cp s3://s3ffmpegtest /mnt/s3ffmpegtest --recursive");
        process = builder.start();
        process.waitFor();
        System.out.println("ec2로 copy");

//        String[] createFilm = new String[]{"docker", "run", "--rm", "-v", "/mnt/mybucket:/videos"
//                , "ffmpeg", "-f", "concat", "-safe", "0", "-i", "merge.txt", "-c", "copy", "output.mp4"};
//        builder = new ProcessBuilder(createFilm);
        builder = new ProcessBuilder();
        builder.command("bash", "-c", "/usr/bin/aws docker run --rm -v /mnt/s3ffmpegtest:/videos " +
                "ffmpeg -f concat -safe 0 -i merge.txt -c copy output.mp4");
        process = builder.start();
        process.waitFor();
        System.out.println("ffmpeg 사용해서 병합");

//        String[] copyFilmToS3 = new String[]{"sudo", "aws", "s3", "cp", "output.mp4", "s3://s3ffmpegtest/output.mp4"};
//        builder = new ProcessBuilder(copyFilmToS3);
        builder = new ProcessBuilder();
        builder.command("bash", "-c", "/usr/bin/aws s3 cp output.mp4 s3://s3ffmpegtest/output.mp4");
        process = builder.start();
        process.waitFor();
        System.out.println("병합한 영상 s3에 업로드");

//        String[] deleteFilmFromLocal = new String[]{"rm", "output.mp4"};
//        builder = new ProcessBuilder(deleteFilmFromLocal);
        builder = new ProcessBuilder();
        builder.command("bash", "-c", "sudo rm output.mp4");
        process = builder.start();
        process.waitFor();
        System.out.println("병합한 영상 삭제");

    }

    public void test(){

        List<String> cmd = new ArrayList<>();
        String str = "sh /home/ubuntu/s3mount.sh";
        cmd.add("bash");
        cmd.add("-c");
        cmd.add(str);
        System.out.println(cmd);
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(new File("/home/ubuntu"));

        try {
            // Run script
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("s3 마운트");
    }
}
