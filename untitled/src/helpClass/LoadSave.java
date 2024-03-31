package helpClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoadSave {


	public static int levelData[][] = new int[20][70];

	public static void SaveLevel(int[][] idArr, File forSave) {
		if (forSave.exists()) {
			WriteToFile(idArr, forSave);
		} else {
			System.out.println("File: " + forSave + " does not exists! ");
			return;
		}
	}

	public static int[][] GetLevelData(File forData) {
		System.out.println(forData);
		if (forData.exists()) {
			levelData =	readCSV(forData);
			return levelData;
		} else {
			System.out.println("File: " + forData + " does not exists! ");
			return null;
		}
	}

	private static void WriteToFile(int idArr[][], File forWriting) {

		try (PrintWriter writer = new PrintWriter(forWriting)) {

			StringBuilder sb = new StringBuilder();

			if(levelData != null) {
				for (int i = 0; i < levelData.length; ++i) {
					for (int j = 0; j < levelData[i].length; ++j) {
						sb.append(idArr[i][j]);
						sb.append(',');
					}
					sb.append('\n');
					writer.write(sb.toString());
					sb.delete(0, sb.length());

				}
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static int[][] readCSV(File file) {
		int i = 0, j = 0;
		try (Scanner sc = new Scanner(file)) {
			sc.useDelimiter(",");
				while (sc.hasNext()) {

					String s = sc.next();
					char arrayC[] = s.toCharArray();

					if(arrayC[0] >= '0' && arrayC[0] <= '9' ) {
						levelData[i][j] = arrayC[0] - 48;
					}
					if(j == 0 && arrayC.length == 2) {
						levelData[i][j] = arrayC[1] - 48;
					}
					++j;

					if(j == 70) {
						j = 0;
						++i;
					}
				}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return levelData;
	}
}


