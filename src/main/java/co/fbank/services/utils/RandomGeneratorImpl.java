package co.fbank.services.utils;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomGeneratorImpl implements RandomGenerator {
	
	@Override
	public Long generateRandomLong() {
		Random randomGen = new Random();
		return new Long(randomGen.nextLong());
	}
}
