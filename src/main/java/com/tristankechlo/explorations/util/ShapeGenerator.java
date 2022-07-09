package com.tristankechlo.explorations.util;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public class ShapeGenerator {

	public static void makeSphere(BlockPos centerPos, double radius, Consumer<BlockPos> consumer) {
		makeEllipsoid(centerPos, radius, radius, radius, consumer);
	}

	public static void makeEllipsoid(BlockPos centerPos, double radiusX, double radiusY, double radiusZ,
			Consumer<BlockPos> consumer) {

		// add 0.5 to all radii so the calculations are from the center of the centerBlock
		radiusX += 0.5D;
		radiusY += 0.5D;
		radiusZ += 0.5D;

		final double invRadiusX = 1 / radiusX;
		final double invRadiusY = 1 / radiusY;
		final double invRadiusZ = 1 / radiusZ;

		final int ceilRadiusX = Mth.ceil(radiusX);
		final int ceilRadiusY = Mth.ceil(radiusY);
		final int ceilRadiusZ = Mth.ceil(radiusZ);

		double nextXn = 0;
		forX: for (int x = 0; x <= ceilRadiusX; ++x) {

			final double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextYn = 0;

			forY: for (int y = 0; y <= ceilRadiusY; ++y) {

				final double yn = nextYn;
				nextYn = (y + 1) * invRadiusY;
				double nextZn = 0;

				forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {

					final double zn = nextZn;
					nextZn = (z + 1) * invRadiusZ;

					double distanceSq = lengthSq(xn, yn, zn);
					if (distanceSq > 1) {
						if (z == 0) {
							if (y == 0) {
								break forX;
							}
							break forY;
						}
						break forZ;
					}

					consumer.accept(centerPos.offset(x, y, z));
					consumer.accept(centerPos.offset(x, y, -z));
					consumer.accept(centerPos.offset(x, -y, z));
					consumer.accept(centerPos.offset(x, -y, -z));
					consumer.accept(centerPos.offset(-x, y, z));
					consumer.accept(centerPos.offset(-x, y, -z));
					consumer.accept(centerPos.offset(-x, -y, z));
					consumer.accept(centerPos.offset(-x, -y, -z));
				}
			}
		}
	}

	public static double lengthSq(double x, double y, double z) {
		return (x * x) + (y * y) + (z * z);
	}

}
