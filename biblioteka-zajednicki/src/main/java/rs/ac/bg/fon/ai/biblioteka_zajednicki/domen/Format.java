package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.io.Serializable;

/**
 * Predstavlja enum klasu koja sadrzi moguce vrednosti formata knjige.
 * 
 * Format knjige moze da bude: MEK_POVEZ, TVRD_POVEZ, EKNJIGA i AUDIOKNJIGA
 * 
 * @author Teodora
 *
 */
public enum Format implements Serializable{
	
	/**
	 * Moguce vrednosti formata knjige.
	 */
    MEK_POVEZ, TVRD_POVEZ, EKNJIGA, AUDIOKNJIGA
    
}
