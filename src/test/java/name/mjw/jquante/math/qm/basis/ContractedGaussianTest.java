package name.mjw.jquante.math.qm.basis;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import name.mjw.jquante.math.qm.basis.ContractedGaussian;
import name.mjw.jquante.math.qm.basis.Power;
import name.mjw.jquante.molecule.Atom;

import org.junit.Before;
import org.junit.Test;

public class ContractedGaussianTest {

	private final double delta = 0.000001;

	ContractedGaussian cgtoS0;

	@Before
	public void setUp() {
		cgtoS0 = new ContractedGaussian(new Vector3D(0, 0, 0), new Power(0, 0, 0));
		cgtoS0.addPrimitive(1.0, 1.0);
		cgtoS0.normalize();
	}

	@Test
	public void testAmplitude() {
		assertEquals(0.712705, cgtoS0.amplitude(new Vector3D(0, 0, 0)), delta);
	}

	@Test
	public void testGetCenteredAtom() {
		Atom H = new Atom("H", 1, new Vector3D(0, 0, 0));
		ContractedGaussian cgto = new ContractedGaussian(H, new Power(0, 0, 0));

		assertEquals(H, cgto.getCenteredAtom());
	}

	@Test
	public void testGetOrigin() {
		assertEquals(new Vector3D(0, 0, 0), cgtoS0.getOrigin());
	}

	@Test
	public void testGetPowers() {
		Power power = new Power(0, 0, 0);
		assertEquals(power, cgtoS0.getPowers());
	}

	@Test
	public void testGetPrimitives() {
		Power power = new Power(0, 0, 0);
		PrimitiveGaussian pg = new PrimitiveGaussian(new Vector3D(0, 0, 0), power, 1, 1);

		assertEquals(pg, cgtoS0.getPrimitives().get(0));
	}

	@Test
	public void testGetNormalization() {
		assertEquals(1.0, cgtoS0.getNormalization(), delta);
	}

	@Test
	public void sTO_3G_1S_H() {
		// https://bse.pnl.gov/bse/portal

		ContractedGaussian cgto = new ContractedGaussian(new Vector3D(0, 0, 0), new Power(0, 0, 0));

		cgto.addPrimitive(3.42525091, 0.154329);
		cgto.addPrimitive(0.62391373, 0.535328);
		cgto.addPrimitive(0.16885540, 0.444636);
		cgto.normalize();

		assertEquals(0.6282471373416881, cgto.amplitude(new Vector3D(0, 0, 0)), delta);
	}

}
