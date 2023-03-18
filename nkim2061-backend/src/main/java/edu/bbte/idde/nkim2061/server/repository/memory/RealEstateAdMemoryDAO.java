package edu.bbte.idde.nkim2061.server.repository.memory;

import edu.bbte.idde.nkim2061.server.model.RealEstateAd;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAdDAO;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public final class RealEstateAdMemoryDAO implements RealEstateAdDAO {

    private final Map<Long, RealEstateAd> realEstateAdMap;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public RealEstateAdMemoryDAO() {
        realEstateAdMap = new TreeMap<>();
    }

    @Override
    public RealEstateAd create(RealEstateAd realEstateAd) {
        realEstateAd.setId(ID_GENERATOR.getAndIncrement());
        realEstateAdMap.put(realEstateAd.getId(), realEstateAd);
        return realEstateAdMap.get(realEstateAd.getId());
    }

    @Override
    public void deleteById(Long id) {
        realEstateAdMap.remove(id);
    }

    @Override
    public RealEstateAd getById(Long id) {
        return realEstateAdMap.get(id);
    }

    @Override
    public List<RealEstateAd> getAll() {
        return realEstateAdMap.values().stream().toList();
    }

    @Override
    public void update(RealEstateAd realEstate) {
        RealEstateAd realEstateAd = realEstateAdMap.get(realEstate.getId());
        if (!realEstate.equals(realEstateAd)) {
            realEstateAdMap.put(realEstate.getId(), realEstate);
        }
    }

    @Override
    public RealEstateAd getByAddress(String address) {
        return realEstateAdMap.values().stream()
                .filter(realEstateAd -> realEstateAd.getAddress().equals(address))
                .findFirst()
                .orElse(null);
    }
}
