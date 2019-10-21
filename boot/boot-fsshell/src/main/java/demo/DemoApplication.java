package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

import javax.security.auth.Subject;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private Configuration configuration;

    @Autowired
    private FsShell shell;

    private FileSystem fileSystem;


    @Override
    public void run(String... args) throws IOException, InterruptedException {
        fileSystem = FileSystem.get(configuration);
        System.out.println("FileSystem: " + fileSystem);
//        for (FileStatus s : shell.lsr("/tmp")) {
//            System.out.println("> " + s.getPath());
//        }
        Path path = new Path("/user/Reaper/dir1");
        final Path path2 = new Path("/rootuser");
        Path root = new Path("/");

        FsPermission fsPermission = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL);
//        fileSystem.setPermission(root,fsPermission);
        System.out.println("Permission: " + fsPermission);

//        System.out.println("ACL for root: " + fileSystem.getAclStatus(path));
        configuration.set("hadoop.security.authentication", "simple");
//        UserGroupInformation.setConfiguration(configuration);
//        UserGroupInformation testUser = UserGroupInformation.createUserForTesting("root", new String[0]);


//        System.setProperty("HADOOP_USER_NAME", "root");
        UserGroupInformation ugiCurrent = UserGroupInformation.getCurrentUser();
        System.out.println("From env: " + System.getProperty("HADOOP_USER_NAME"));
        System.out.println("Current User Group Info: " + ugiCurrent);

        System.out.println("Created: " + fileSystem.mkdirs(path2));
//        System.out.println("ACL status:" + fileSystem.getAclStatus(path));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
