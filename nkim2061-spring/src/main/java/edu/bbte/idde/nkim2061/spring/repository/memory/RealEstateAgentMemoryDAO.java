package edu.bbte.idde.nkim2061.spring.repository.memory;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAgentDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@Profile("dev")
public final class RealEstateAgentMemoryDAO implements RealEstateAgentDAO {

    private final Map<Long, RealEstateAgent> realEstateAgentMap;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public RealEstateAgentMemoryDAO() {
        realEstateAgentMap = new TreeMap<>();
    }

    @Override
    public RealEstateAgent save(RealEstateAgent realEstateAgent) {
        if (realEstateAgent.getId() == null) {
            update(realEstateAgent);
        } else {
            realEstateAgent.setId(ID_GENERATOR.getAndIncrement());
            realEstateAgentMap.put(realEstateAgent.getId(), realEstateAgent);
        }
        return realEstateAgentMap.get(realEstateAgent.getId());
    }

    @Override
    public void deleteById(Long id) {
        realEstateAgentMap.remove(id);
    }

    @Override
    public Optional<RealEstateAgent> findById(Long id) {
        return Optional.ofNullable(realEstateAgentMap.get(id));
    }

    @Override
    public List<RealEstateAgent> findAll() {
        return realEstateAgentMap.values().stream().toList();
    }

    public void update(RealEstateAgent realEstateAgent) {
        RealEstateAgent realEstateAgent1 = realEstateAgentMap.get(realEstateAgent.getId());
        if (!realEstateAgent.equals(realEstateAgent1)) {
            realEstateAgentMap.put(realEstateAgent.getId(), realEstateAgent);
        }
    }

    @Override
    public RealEstateAgent getByEmail(String email) {
        return realEstateAgentMap.values().stream()
                .filter(realEstateAgent -> realEstateAgent.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
