package edu.bbte.idde.nkim2061.server.repository.memory;

import edu.bbte.idde.nkim2061.server.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAgentDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public final class RealEstateAgentMemoryDAO implements RealEstateAgentDAO {

    private final Map<Long, RealEstateAgent> realEstateAgentMap;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public RealEstateAgentMemoryDAO() {
        realEstateAgentMap = new TreeMap<>();
    }

    @Override
    public RealEstateAgent create(RealEstateAgent realEstateAgent) {
        realEstateAgent.setId(ID_GENERATOR.getAndIncrement());
        realEstateAgentMap.put(realEstateAgent.getId(), realEstateAgent);
        return realEstateAgentMap.get(realEstateAgent.getId());
    }

    @Override
    public void deleteById(Long id) {
        realEstateAgentMap.remove(id);
    }

    @Override
    public RealEstateAgent getById(Long id) {
        return realEstateAgentMap.get(id);
    }

    @Override
    public List<RealEstateAgent> getAll() {
        return realEstateAgentMap.values().stream().toList();
    }

    @Override
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
