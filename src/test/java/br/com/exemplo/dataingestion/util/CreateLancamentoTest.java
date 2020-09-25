package br.com.exemplo.dataingestion.util;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

class CreateLancamentoTest {

    @SneakyThrows
    @org.junit.jupiter.api.Test
    void getIdConta() {
        CreateLancamento createLancamento = new CreateLancamento(new Faker());
        Set<UUID> set = new HashSet<>();
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