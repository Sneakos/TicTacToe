
/**
 * Write a description of class FileReader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.ArrayList;

public class FileReadWrite
{
    BufferedReader br;
    BufferedWriter bw;
    String fileName;
    File file;
    File tempFile;

    public FileReadWrite(String name, boolean append)
    {
        file = new File(name);
        fileName = file.getAbsolutePath();
        file = new File(fileName);
        if(!append)
            file.delete();
    }

    public ArrayList<String> read()
    {
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        try{
            FileReader fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while((line = br.readLine()) != null)
                lines.add(line);
            fr.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return lines;
    }

    public String findLine(String word)
    {
        String line;
        try{
            FileReader fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while((line = br.readLine()) != null)
            {
                String tmp = line.split(" ")[0];
                if(tmp.equals(word))
                {
                    return line;
                }
            }
            fr.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return "";
    }

    public String findClosest(String word)
    {
        String line;
        String largest = "";
        int longest = 0;
        try{
            FileReader fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while((line = br.readLine()) != null)
            {
                if(! line.equals(""))
                {
                    int num = getLike(word, line);
                    if(num > longest)
                    {
                        longest = num;
                        largest = line;
                    }
                }
            }
            fr.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return largest;
    }

    public void write(String ... lines)
    {
        try{
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            for(String line : lines)
            {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
    }

    public void replace(String newLine, int choice, int moves, boolean good)
    {
        tempFile = new File(fileName + ".tmp");
        try{
            FileWriter fw = new FileWriter(tempFile);
            bw = new BufferedWriter(fw);
            FileReader fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line = "";
            while((line = br.readLine()) != null)
            {
                if(!line.equals(newLine))
                {   
                    bw.write(line);
                    bw.newLine();
                } else {
                    String weights = line.split(" ")[1];
                    String toAdd = line.split(" ")[0] + " ";
                    String nodes[] = weights.split("[)]");
                    int changed = (int) Integer.parseInt(nodes[choice].split("[(]")[1]);
                    if(good)
                    {
                        changed += moves;
                        if(changed > 100)
                            changed = 100;
                    }
                    else
                    {
                        changed -= moves;
                        if(changed < 0)
                            changed = 0;
                    }
                    for(int i = 0; i < nodes.length; i++)
                    {
                        if(i == choice)
                        {
                            toAdd += choice + "(" + changed + ")";
                        } else {
                            toAdd += nodes[i] + ")";
                        }
                    }
                    bw.write(toAdd);
                    bw.newLine();
                }
            }
            fr.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
        update();
    }

    public void update()
    {
        System.gc();
        file.setWritable(true);
        file.delete();
        tempFile.renameTo(file);
    }

    public int getLike(String line1, String line2)
    {
        String lines1[] = line1.split("");
        String lines2[] = line2.split("");
        int count = 0;
        for(int i = 0; i < lines1.length; i++)
        {
            if(lines1[i].equals(lines2[i]))
                count++;
        }

        return count;
    }
}
