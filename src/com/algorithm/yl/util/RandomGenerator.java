/* Copyright 2009-2016 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.algorithm.yl.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.MathArrays;

/**
 * Generates weights randomly.  This is the method proposed in [1] to replace
 * the normal boundary intersection method in the original MOEA/D.  If {@code N}
 * weights are requested, this method first generates {@code 50*N} random
 * weights.  From these weights, {@code N} are selected that are maximally
 * distant from all other weights.
 * <p>
 * References:
 * <ol>
 * <li>Zhang, Q., et al.  "The Performance of a New Version of MOEA/D on
 *     CEC09 Unconstrained MOP Test Instances."  IEEE Congress on Evolutionary
 *     Computation, 2009.
 * </ol>
 */
public class RandomGenerator implements WeightGenerator {
	
	/**
	 * The number of objectives.
	 */
	private final int numberOfObjectives;
	
	/**
	 * The number of weights to generate.
	 */
	private final int numberOfPoints;  //populationSize 种群大小
	
	/**
	 * Constructs a new weight generator that generates randomly-sampled
	 * weights.
	 * 
	 * @param numberOfObjectives the number of objectives
	 * @param numberOfPoints the number of weights to generate
	 */
	public RandomGenerator(int numberOfObjectives, int numberOfPoints) {
		super();
		this.numberOfObjectives = numberOfObjectives;
		this.numberOfPoints = numberOfPoints;   //即MOEA   populationSize 种群大小
	}
	
	/**
	 * Returns the weights for 2D problems.
	 * 
	 * @return the weights
	 */
	private List<double[]> initializeWeights2D() {
		List<double[]> weights = new ArrayList<double[]>();
		
		// add boundary weights
		weights.add(new double[] { 0.0, 1.0 });
		weights.add(new double[] { 1.0, 0.0 });

		for (int i = 1; i < numberOfPoints - 1; i++) {
			double a = i / (double)(numberOfPoints - 1);
			weights.add(new double[] { a, 1 - a });
		}
		
		return weights;
	}

	/**
	 * Returns the weights for problems of arbitrary dimension.
	 * 
	 * @return the weights
	 */
	private List<double[]> initializeWeightsND() { //创建均匀分布的权重向量
		int N = 50;
		List<double[]> candidates = new ArrayList<double[]>(numberOfPoints * N);

		// create random weights
		for (int i = 0; i < numberOfPoints * N; i++) {
			double[] weight = new double[numberOfObjectives];  //目标个数
			
			for (int j = 0; j < numberOfObjectives; j++) {
				weight[j] = PRNG.nextDouble();   //随机生成
			}
			
			double sum = StatUtils.sum(weight);  //统计weight中所有值的和
			
			for (int j = 0; j < numberOfObjectives; j++) {
				weight[j] /= sum;
			}   //变成初始向量

			candidates.add(weight);  
		}
		
		List<double[]> weights = new ArrayList<double[]>(numberOfPoints * N);   

		// add boundary weights (1,0,...,0), (0,1,...,0), ..., (0,...,0,1)
		for (int i = 0; i < numberOfObjectives; i++) {       //边界向量
			double[] weight = new double[numberOfObjectives];
			weight[i] = 1.0;
			weights.add(weight);
		}

		// fill in remaining weights with the weight vector with the largest
		// distance from the assigned weights
		while (weights.size() < numberOfPoints) {    // 用所分配的权重与最大距离的权重向量填充剩余的权重 
			double[] weight = null;
			double distance = Double.NEGATIVE_INFINITY;

			for (int i = 0; i < candidates.size(); i++) {   //找随机生成的向量中与任意边界向量最近的向量作为权重向量
				double d = Double.POSITIVE_INFINITY;  //double中表示  1/0

				for (int j = 0; j < weights.size(); j++) {
					d = Math.min(d, MathArrays.distance(candidates.get(i),
							weights.get(j)));     //candidates存储的是里面产生的随机向量
				}

				if (d > distance) {
					weight = candidates.get(i);
					distance = d;
				}
			}

			weights.add(weight);
			candidates.remove(weight);
		}
		
		return weights;
	}

	public int size() {
		return numberOfPoints;
	}

	public List<double[]> generate() {
		if (numberOfObjectives == 2) {
			return initializeWeights2D();
		} else {
			return initializeWeightsND();
		}
	}

}
