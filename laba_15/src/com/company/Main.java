package com.company;
import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Enter file with file resolution: ");
        Scanner sc = new Scanner(System.in);
        String test = sc.next();

        FileSearch fileSearch =  new FileSearch("D:\\12",test);



        fileSearch.start();
        fileSearch.join();

        System.out.println("Результат");

        for(String s: fileSearch.result)
        {
            System.out.println(s);
        }
    }



    static class FileSearch extends Thread
    {
        ArrayList<String> result;
        String path;
        String find;




        public void func() throws InterruptedException
        {

            ArrayList<FileSearch> Threads = new ArrayList<FileSearch>();

            File f = new File(path);
            System.out.println((f));

            System.out.println("Поиск в:" + f);


            File[] list = f.listFiles();
            if(list != null)
                for (File file : list)
                {
                    if (find.equals(file.getName())) {
                        System.out.println(file);
                        result.add(file.toString());
                    }
                    File tempfile = new File(path + "\\" + file.getName());
                    if (tempfile.isDirectory())
                    {


                        FileSearch F = new FileSearch(tempfile.toString(), find);
                        F.start();
                        Threads.add(F);


                    }
                }

            for(FileSearch Thread : Threads )
            {
                Thread.join();
            }

            for(FileSearch Thread : Threads )
            {
                if(!Thread.result.isEmpty())
                {
                    result.addAll(Thread.result);
                }
            }

        }

        public FileSearch( String path, String find) {

            result = new ArrayList<String>();
            this.path = path;
            this.find = find;
        }

        @Override
        public void run() {
            try {
                func();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
