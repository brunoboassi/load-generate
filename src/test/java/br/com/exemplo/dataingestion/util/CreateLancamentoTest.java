package br.com.exemplo.dataingestion.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lombok.SneakyThrows;

class CreateLancamentoTest {

    @SneakyThrows
    @org.junit.jupiter.api.Test
    void getIdConta() {
        FileOutputStream outputStream = new FileOutputStream("MyFile.txt");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            for(int i = 0; i<1000000;i++)
            {
                bufferedWriter.write(UUID.nameUUIDFromBytes(StringUtils.leftPad(String.valueOf(i),12,'0').getBytes()).toString());
                bufferedWriter.newLine();
            }
        bufferedWriter.close();

    }
}