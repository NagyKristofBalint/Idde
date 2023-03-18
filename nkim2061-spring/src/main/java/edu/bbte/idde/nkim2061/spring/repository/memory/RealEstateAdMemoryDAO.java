package edu.bbte.idde.nkim2061.spring.repository.memory;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAdDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
public final class RealEstateAdMemoryDAO implements RealEstateAdDAO {

    private final Map<Long, RealEstateAd> realEstateAdMap;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public RealEstateAdMemoryDAO() {
        realEstateAdMap = new TreeMap<>();
    }

    @Override
    public RealEstateAd save(RealEstateAd realEstateAd) {
        if (realEstateAd.getId() == null) {
            update(realEstateAd);
        } else {
            realEstateAd.setId(ID_GENERATOR.getAndIncrement());
            realEstateAdMap.put(realEstateAd.getId(), realEstateAd);
        }
        return realEstateAdMap.get(realEstateAd.getId());
    }

    @Override
    public void deleteById(Long id) {
        realEstateAdMap.remove(id);
    }

    @Override
    public Optional<RealEstateAd> findById(Long id) {
        return Optional.ofNullable(realEstateAdMap.get(id));
    }

    @Override
    public List<RealEstateAd> findAll() {
        return realEstateAdMap.values().stream().toList();
    }

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
