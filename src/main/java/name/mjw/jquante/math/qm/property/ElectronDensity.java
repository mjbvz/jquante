package name.mjw.jquante.math.qm.property;

import java.util.ArrayList;

import name.mjw.jquante.math.Vector;
import name.mjw.jquante.math.geom.Point3D;
import name.mjw.jquante.math.qm.BasisFunctions;
import name.mjw.jquante.math.qm.Density;
import name.mjw.jquante.math.qm.SCFMethod;
import name.mjw.jquante.math.qm.basis.ContractedGaussian;

/**
 * Computes electron density on specified points for a Molecule.
 * 
 * @author V.Ganesh
 * @version 2.0 (Part of MeTA v2.0)
 */
public class ElectronDensity extends OneElectronProperty {

	private int nbf;

	private ArrayList<ContractedGaussian> bfs;

	private double[][] dm;

	/**
	 * Creates a new instance of ElectronDensity
	 * 
	 * @param scfMethod
	 *            the Self Consistent Field (SCF) method
	 */
	public ElectronDensity(SCFMethod scfMethod) {
		super(scfMethod);

		bfs = scfMethod.getOneEI().getBasisFunctions().getBasisFunctions();
		nbf = bfs.size();

		dm = scfMethod.getDensity().getData();
	}

	/**
	 * Creates a new instance of ElectronDensity
	 * 
	 * @param bfs
	 *            the basis functions of a given molecule and a basis set
	 * @param den
	 *            the density matrix
	 */
	public ElectronDensity(BasisFunctions bfs, Density den) {
		this.bfs = bfs.getBasisFunctions();
		this.nbf = this.bfs.size();

		this.dm = den.getData();
	}

	/**
	 * Computes the one electron property on the specified point and returns its
	 * value at the specified point. <br>
	 * Note that the unit of Point3D object must be a.u. No attempt is made to
	 * verify this.
	 * 
	 * @param point
	 *            the point of interest
	 * @return the value of this property at this point
	 */
	@Override
	public double compute(Point3D point) {
		Vector amplitides = new Vector(nbf);
		double[] amp = amplitides.getVector();
		double density = 0.0, amp_xyz;
		int m, l;

		for (m = 0; m < nbf; m++)
			amp[m] = bfs.get(m).amplitude(point);

		for (m = 0; m < nbf; m++) {
			amp_xyz = 0.0;
			for (l = 0; l < nbf; l++) {
				amp_xyz += dm[m][l] * amp[l];
			} // end for
			density += amp_xyz * amp[m];
		} // end for

		// 2.0 represents double occupancy
		return 2.0 * density;
	}
}
