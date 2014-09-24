package chat.foda.pra.caralho.jdbc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

	private static File log = new File("log.txt");

	public static boolean existeLog() {
		return log.exists();
	}

	public static boolean criarArqLog() {
		try {
			if (log.createNewFile()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void escrever(String texto) {
		try {
			FileWriter fw = new FileWriter(log, true);
			BufferedWriter bw = new BufferedWriter(fw);

			System.out.print("Usuario: ");
			bw.write(texto);
			bw.newLine();
			
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] ler() {
		String[] dados = new String[2];
		try {
			FileReader fr = new FileReader(log);
			BufferedReader br = new BufferedReader(fr);
			int i = 0;

			while (br.ready()) {
				String linha = br.readLine();
				dados[i] = linha;
				i++;
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dados;
	}
}
