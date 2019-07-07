package name.mjw.jquante.math.qm;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import name.mjw.jquante.config.impl.AtomInfo;
import name.mjw.jquante.math.qm.basis.BasisSetLibrary;
import name.mjw.jquante.math.qm.basis.ContractedGaussian;
import name.mjw.jquante.molecule.Molecule;

/**
 * The 1E integral (overlap S matrix) and 1E hCore matrices driver.
 * 
 * @author V.Ganesh
 * @version 2.0 (Part of MeTA v2.0)
 */
public class OneElectronIntegrals {

	private static final Logger LOG = LogManager.getLogger(OneElectronIntegrals.class);

	/**
	 * The overlap S matrix.
	 */
	private Overlap overlap;

	/**
	 * The core Hamiltonian matrix contains integrals that represent the kinetic
	 * energy of an electron (T) and electron-nuclear potential energy (V).
	 */
	private HCore hCore;

	private BasisSetLibrary basisSetLibrary;
	private Molecule molecule;

	/**
	 * Creates a new instance of OneElectronIntegrals
	 * 
	 * @param basisSetLibrary the basis functions to be used
	 * @param mol             the Molecule object, of whose 1E integrals are to be
	 *                        evaluated
	 */
	public OneElectronIntegrals(BasisSetLibrary basisSetLibrary, Molecule mol) {
		this.basisSetLibrary = basisSetLibrary;
		this.molecule = mol;

		// compute the 1E integrals, form S matrix and hCore
		compute1E();
	}

	/**
	 * Get BasisFunctions associated with this 1E evaluation
	 * 
	 * @return instance of basisFunctions
	 */
	public BasisSetLibrary getBasisSetLibrary() {
		return basisSetLibrary;
	}

	/**
	 * compute the 1E integrals, form S matrix and hCore
	 */
	protected void compute1E() {
		List<ContractedGaussian> bfs = basisSetLibrary.getBasisFunctions();
		int noOfBasisFunctions = bfs.size();
		int atomIndex;

		// allocate memory
		this.overlap = new Overlap(noOfBasisFunctions);
		this.hCore = new HCore(noOfBasisFunctions);

		// read in the atomic numbers
		int[] atomicNumbers = new int[molecule.getNumberOfAtoms()];
		AtomInfo ai = AtomInfo.getInstance();

		for (atomIndex = 0; atomIndex < atomicNumbers.length; atomIndex++) {
			atomicNumbers[atomIndex] = ai.getAtomicNumber(molecule.getAtom(atomIndex).getSymbol());
		}

		// set up the S matrix and the hCore h
		IntStream.range(0, noOfBasisFunctions).parallel().forEach(i -> {
			ContractedGaussian bfi = bfs.get(i);

			IntStream.range(0, noOfBasisFunctions).parallel().forEach(j -> {
				ContractedGaussian bfj = bfs.get(j);

				overlap.setEntry(i, j, bfi.overlap(bfj)); // the overlap matrix
				hCore.setEntry(i, j, bfi.kinetic(bfj));// KE matrix elements

				for (int k = 0; k < atomicNumbers.length; k++) {
					hCore.setEntry(i, j, (hCore.getEntry(i, j)
							+ atomicNumbers[k] * bfi.nuclear(bfj, molecule.getAtom(k).getAtomCenterInAU())));
				}
			});
		});

	}

	/**
	 * Getter for property overlap.
	 * 
	 * @return Value of property overlap.
	 */
	public Overlap getOverlap() {
		return this.overlap;
	}

	/**
	 * Getter for property hCore.
	 * 
	 * @return Value of property hCore.
	 */
	public HCore getHCore() {
		return this.hCore;
	}

}
