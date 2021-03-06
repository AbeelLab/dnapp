package nl.tudelft.ti2806.pl1.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.jfree.ui.ExtensionFileFilter;

/**
 * The file chooser dialog used for importing files.
 * 
 * @author Maarten
 * @since 20-5-2015
 */
public class ImportDialog extends JFileChooser {

	/** The serial version UID. */
	private static final long serialVersionUID = -3764514151291068712L;

	/** The type of files this dialog will be able to load. */
	private ImportType filterType;

	/**
	 * Initialize the import dialog.
	 * 
	 * @param type
	 *            The type of files this dialog should be able to load.
	 */
	public ImportDialog(final ImportType type) {
		this(type, (File) null);
	}

	/**
	 * Initialize the import dialog.
	 * 
	 * @param type
	 *            The type of files this dialog should be able to load.
	 * @param currentDirectory
	 *            The directory to start from.
	 */
	public ImportDialog(final ImportType type, final File currentDirectory) {
		super(currentDirectory);
		filterType = type;
		setDialogType(JFileChooser.OPEN_DIALOG);
		setFileFilter(filterType.getFileFilter());
		setDialogTitle(filterType.getTitle());
		setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	/**
	 * Enumeration of the type of files the application can import. All types
	 * have a file filter.
	 * 
	 * @author Maarten
	 * @since 20-5-2015
	 */
	public enum ImportType {

		/**
		 * Only graph node files will be shown.
		 */
		NODES(new GraphNodeFileFilter(), "Load node file"),

		/** Only graph edge files will be shown. */
		EDGES(new GraphEdgeFileFilter(), "Load edge file"),

		/** Only phylo files will be shown. */
		PHYLO(new ExtensionFileFilter("Newick phylogenetic tree file", "nwk"),
				"Load phylogenetic tree file"),

		/** Only gene annotation files will be shown. */
		GENE_ANNOTATION(new ExtensionFileFilter("General Feature Format file",
				"gff"), "Load gene annotation"),

		/** Only known resistance mutations will be shown. */
		KNOWN_RES_MUT(new ExtensionFileFilter(
				"Resistance Causing Mutations file", "rcm"),
				"Load resistance causing mutations file.");

		/** The type filter of the import type. */
		private FileFilter filter;

		/** The title for the file chooser dialog. */
		private String title;

		/**
		 * Create a new FileFilter.
		 * 
		 * @param inFilter
		 *            The type of file filter.
		 * @param inTitle
		 *            The title for the file chooser dialog.
		 */
		ImportType(final FileFilter inFilter, final String inTitle) {
			filter = inFilter;
			title = inTitle;
		}

		/**
		 * @return a file filter accepting only files for this type of
		 *         importable files.
		 */
		public FileFilter getFileFilter() {
			return filter;
		}

		/**
		 * @return The title for the file chooser dialog this type of importable
		 *         files.
		 */
		public String getTitle() {
			return title;
		}
	}

}
