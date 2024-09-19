package com.example.demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ColumnRangePartitioner implements Partitioner {

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		log.info("gridSize :: {}", gridSize);
		Map<String, ExecutionContext> result = new HashMap<>();

		int noOfLines = 0;
		try {
			// Exclude header
			noOfLines = getNoOfLines("customers.csv") - 1;
		} catch (IOException e) {
			e.printStackTrace();
		}

		int min = 1;
		int targetSize = (noOfLines - min) / gridSize + 1;

		int number = 0;
		int start = min;
		int end = start + targetSize - 1;

		while (start <= noOfLines) {
			ExecutionContext value = new ExecutionContext();
			result.put("partition" + number, value);

			if (end >= noOfLines) {
				end = noOfLines;
			}

			value.putInt("minValue", start);
			value.putInt("maxValue", end);
			log.info("Partition: {}:: {}"+ start, end);
			
			start += targetSize;
			end += targetSize;

			number++;
		}
		return result;
	}

//	@Override
//	public Map<String, ExecutionContext> partition(int gridSize) {
//		Map<String, ExecutionContext> result = new HashMap<>();
//
//		int noOfLines = 0;
//		try {
//			noOfLines = getNoOfLines("customers.csv") - 1;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		int firstLine = 1;
//		int lastLine = gridSize;
//		int partitionNumber = 0;
//
//		while(firstLine < noOfLines) {
//			if(lastLine >= noOfLines) {
//				lastLine = noOfLines;
//			}
//			log.info("Partition number : {}, first line is : {}, last  line is : {} ", partitionNumber, firstLine, lastLine);
//
//			ExecutionContext value = new ExecutionContext();
//			value.putLong("partition_number", partitionNumber);
//			value.putLong("first_line", firstLine);
//			value.putLong("last_line", lastLine);
//
//			result.put("PartitionNumber-" + partitionNumber, value);
//
//			firstLine = firstLine + gridSize;
//			lastLine = lastLine + gridSize;
//			partitionNumber++;
//		}
//		log.info("No of lines {}", noOfLines);
//		return result;
//	}

	public int getNoOfLines(String fileName) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(fileName);
		try (LineNumberReader reader = new LineNumberReader(
				new FileReader(classPathResource.getFile().getAbsolutePath()))) {
			reader.skip(Integer.MAX_VALUE);
			return reader.getLineNumber();
		}
	}
}
