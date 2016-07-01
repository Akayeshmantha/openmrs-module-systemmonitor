package org.openmrs.module.systemmonitor.indicator;

/**
 * All memory whether RAM or SWAP is in MEga Bytes (MB), this is meant to
 * support may be persisting memory to the db so that we can monitor last
 * day/week or year
 */
public class MemoryIndicator {
	/**
	 * Free RAM, in Mega Bytes
	 */
	private Long freeMemory;

	/**
	 * Total RAM, in Mega Bytes
	 */
	private Long totalMemory;

	/**
	 * Used RAM, in Mega Bytes
	 */
	private Long usedMemory;

	private Long totalSWAPMemory;

	private Long usedSWAPMemory;

	private Long freeSWAPMemory;
}
