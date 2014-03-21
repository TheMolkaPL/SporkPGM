package io.sporkpgm.region;

import io.sporkpgm.exception.InvalidRegionException;
import io.sporkpgm.region.regions.BlockRegion;
import io.sporkpgm.region.regions.CircleRegion;
import io.sporkpgm.region.regions.ComplementRegion;
import io.sporkpgm.region.regions.CuboidRegion;
import io.sporkpgm.region.regions.CylinderRegion;
import io.sporkpgm.region.regions.IntersectRegion;
import io.sporkpgm.region.regions.NegativeRegion;
import io.sporkpgm.region.regions.RectangleRegion;
import io.sporkpgm.region.regions.SphereRegion;
import io.sporkpgm.region.regions.UnionRegion;
import io.sporkpgm.util.XMLUtils;

import java.util.List;

import org.bukkit.util.Vector;
import org.dom4j.Element;

import com.google.common.collect.Lists;

public class RegionParser {

	private RegionContainer container;

	public RegionParser(RegionContainer container) {
		this.container = container;
	}

	public Region parse(Element ele) throws InvalidRegionException {
		String type = ele.getName();

		Region region = null;

		if (type.equalsIgnoreCase("region"))
			region = parseRegion(ele);
		else if (type.equalsIgnoreCase("rectangle"))
			region = parseRectangle(ele);
		else if (type.equalsIgnoreCase("cuboid"))
			region = parseCuboid(ele);
		else if (type.equalsIgnoreCase("circle"))
			region = parseCircle(ele);
		else if (type.equalsIgnoreCase("cylinder"))
			region = parseCylinder(ele);
		else if (type.equalsIgnoreCase("sphere"))
			region = parseSphere(ele);
		else if (type.equalsIgnoreCase("block"))
			region = parseBlock(ele);
		else if (type.equalsIgnoreCase("negative"))
			region = parseNegative(ele);
		else if (type.equalsIgnoreCase("union"))
			region = parseUnion(ele);
		else if (type.equalsIgnoreCase("complement"))
			region = parseComplement(ele);
		else if (type.equalsIgnoreCase("intersect"))
			region = parseIntersect(ele);

		if (region != null && ele.attributeValue("name") != null && !type.equalsIgnoreCase("region"))
			container.add(ele.attributeValue("name"), region);
		else if(region != null)
			container.add(region);
		
		return region;
	}

	public List<Region> parseSubRegions(Element ele) throws InvalidRegionException {
		List<Region> regions = Lists.newArrayList();
		for (Element e : XMLUtils.getElements(ele))
			regions.add(parse(e));
		return regions;
	}

	public Region parseRegion(Element ele) throws InvalidRegionException {
		String name = ele.attributeValue("name");
		if (container.get(name) != null)
			return container.get(name);
		throw new InvalidRegionException("No named region found by '" + name + "'");
	}

	public RectangleRegion parseRectangle(Element ele) throws InvalidRegionException {
		List<Double> min = XMLUtils.parse2DVector(ele.attributeValue("min"));
		List<Double> max = XMLUtils.parse2DVector(ele.attributeValue("max"));
		if (min == null || min.isEmpty() || max == null || max.isEmpty())
			throw new InvalidRegionException("RectangleRegion needs a min and a max value");
		return new RectangleRegion(min.get(0), min.get(1), max.get(0), max.get(1));
	}

	public CuboidRegion parseCuboid(Element ele) throws InvalidRegionException {
		Vector min = XMLUtils.parseVector(ele.attributeValue("min"));
		Vector max = XMLUtils.parseVector(ele.attributeValue("max"));
		if (min == null || max == null)
			throw new InvalidRegionException("CuboidRegion needs a min and a max value");
		return new CuboidRegion(min, max);
	}

	public CircleRegion parseCircle(Element ele) throws InvalidRegionException {
		List<Double> center = XMLUtils.parse2DVector(ele.attributeValue("center"));
		Double radius = XMLUtils.parseDouble(ele.attributeValue("radius"));
		if (center == null || center.isEmpty() || radius == null || radius == 0.0)
			throw new InvalidRegionException("CircleRegion needs a center and a radius value");
		return new CircleRegion(center.get(0), center.get(1), radius);
	}

	public CylinderRegion parseCylinder(Element ele) throws InvalidRegionException {
		Vector base = XMLUtils.parseVector(ele.attributeValue("base"));
		Double radius = XMLUtils.parseDouble(ele.attributeValue("radius"));
		Double height = XMLUtils.parseDouble(ele.attributeValue("height"));
		if (base == null || radius == null || radius == 0.0 || height == null)
			throw new InvalidRegionException("CylinderRegion needs a base, radius and a height value");
		return new CylinderRegion(base, radius, height);
	}

	public SphereRegion parseSphere(Element ele) throws InvalidRegionException {
		Vector center = XMLUtils.parseVector(ele.attributeValue("center"));
		Double radius = XMLUtils.parseDouble(ele.attributeValue("radius"));
		if (center == null || radius == null || radius == 0.0)
			throw new InvalidRegionException("SphereRegion needs a center and a radius value");
		return new SphereRegion(center, radius);
	}

	public BlockRegion parseBlock(Element ele) throws InvalidRegionException {
		Vector block = XMLUtils.parseVector(ele.getText());
		if (block == null)
			throw new InvalidRegionException("BlockRegion must have a block value");
		return new BlockRegion(block);
	}

	public NegativeRegion parseNegative(Element ele) throws InvalidRegionException {
		List<Region> regions = parseSubRegions(ele);
		if (regions == null || regions.isEmpty() || regions.size() == 1)
			throw new InvalidRegionException("NegativeRegion must have 1 region");
		return new NegativeRegion(regions.get(0));
	}

	public UnionRegion parseUnion(Element ele) throws InvalidRegionException {
		List<Region> regions = parseSubRegions(ele);
		if (regions == null || regions.isEmpty() || regions.size() > 1)
			throw new InvalidRegionException("UnionRegion must have at least 2 regions");
		return new UnionRegion(regions);
	}

	public ComplementRegion parseComplement(Element ele) throws InvalidRegionException {
		List<Region> regions = parseSubRegions(ele);
		if (regions == null || regions.isEmpty() || regions.size() > 1)
			throw new InvalidRegionException("ComplementRegion must have at least 2 regions");
		return new ComplementRegion(regions.get(0), regions.subList(0, regions.size() - 1));
	}
	
	public IntersectRegion parseIntersect(Element ele) throws InvalidRegionException {
		List<Region> regions = parseSubRegions(ele);
		if (regions == null || regions.isEmpty() || regions.size() > 1)
			throw new InvalidRegionException("IntersectRegion must have at least 2 regions");
		return new IntersectRegion(regions);
	}
}
