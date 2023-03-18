package edu.bbte.idde.nkim2061.webapp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Template;
import edu.bbte.idde.nkim2061.server.controller.RealEstateAdController;
import edu.bbte.idde.nkim2061.server.controller.exception.BadRequestException;
import edu.bbte.idde.nkim2061.server.controller.exception.ControllerException;
import edu.bbte.idde.nkim2061.server.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAdCreationDTO;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAdUpdateDTO;
import edu.bbte.idde.nkim2061.server.dto.outgoing.RealEstateAdResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@WebServlet("/real-estate-ads-template")
public class RealEstateAdServletWithTemplate extends HttpServlet {

    private final RealEstateAdController realEstateAdController;
    private final ObjectMapper objectMapper;

    public RealEstateAdServletWithTemplate() {
        super();
        realEstateAdController = new RealEstateAdController();
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /real-estate-ads-template");
        resp.setHeader("Content-Type", "text/html");
        try {
            if (req.getParameter("id") == null) {
                try {
                    Map<String, List<RealEstateAdResponseDTO>> model = new ConcurrentHashMap<>();
                    model.put("ads", (List<RealEstateAdResponseDTO>) realEstateAdController.findAll());
                    Template template = TemplateFactory.getTemplate("index");
                    template.apply(model, resp.getWriter());
                } catch (IOException e) {
                    log.error("Error while writing to response. {}", e.getMessage());
                    resp.setStatus(500);
                }
            } else {
                try {
                    Map<String, List<RealEstateAdResponseDTO>> model = new ConcurrentHashMap<>();
                    model.put("ads", List.of(realEstateAdController.findById(Long.parseLong(req.getParameter("id")))));
                    Template template = TemplateFactory.getTemplate("index");
                    template.apply(model, resp.getWriter());
                } catch (NotFoundException e) {
                    log.warn("RealEstateAd with id {} does not exist", req.getParameter("id"));
                    resp.getWriter().println("RealEstateAd with id " + req.getParameter("id") + " does not exist");
                    resp.setStatus(404);
                } catch (NumberFormatException e) {
                    log.error("Invalid id: {}", req.getParameter("id"));
                    resp.setStatus(400);
                } catch (IOException e) {
                    log.error("Error while writing response {}", e.getMessage());
                    resp.setStatus(500);
                }
            }
        } catch (ControllerException e) {
            log.error("Error while processing request {}", e.getMessage());
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST /real-estate-ads-template");
        resp.setHeader("Content-Type", "application/json");
        try {
            RealEstateAdResponseDTO response = realEstateAdController
                    .save(objectMapper.readValue(req.getReader(), RealEstateAdCreationDTO.class));
            objectMapper.writeValue(resp.getOutputStream(),
                    objectMapper.convertValue(response, RealEstateAdResponseDTO.class));
            resp.setStatus(201);
        } catch (IOException e) {
            log.error("Error while reading request body. {}", e.getMessage());
            resp.setStatus(500);
        } catch (BadRequestException e) {
            log.error("Constraint violations while saving RealEstateAd");
            try {
                resp.getWriter().print(e.getMessage());
                resp.setStatus(400);
            } catch (IOException ioException) {
                log.error("Error while writing to response. {}", ioException.getMessage());
                resp.setStatus(500);
            }
        } catch (ControllerException e) {
            log.error("Error while processing request {}", e.getMessage());
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT /real-estate-ads-template");
        try {
            realEstateAdController.update(objectMapper.readValue(req.getReader(), RealEstateAdUpdateDTO.class));
        } catch (IOException e) {
            log.error("Error while reading request body. {}", e.getMessage());
            resp.setStatus(500);
        } catch (BadRequestException e) {
            log.error("Constraint violations while updating RealEstateAd");
            try {
                resp.getWriter().print(e.getMessage());
                resp.setStatus(400);
            } catch (IOException ioException) {
                log.error("Error while writing to response. {}", ioException.getMessage());
                resp.setStatus(500);
            }
        } catch (NotFoundException e) {
            log.warn("RealEstateAd with id {} does not exist", req.getParameter("id"));
            resp.setStatus(404);
        } catch (ControllerException e) {
            log.error("Error while processing request {}", e.getMessage());
            resp.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE /real-estate-ads-template");
        if (req.getParameter("id") == null) {
            log.warn("No id provided");
            try {
                resp.getWriter().print("No id provided");
                resp.setStatus(400);
            } catch (IOException e) {
                log.error("Error while writing to response. {}", e.getMessage());
                resp.setStatus(500);
            }
        } else {
            try {
                realEstateAdController.deleteById(Long.parseLong(req.getParameter("id")));
                resp.setStatus(204);
            } catch (NotFoundException e) {
                log.warn("RealEstateAd with id {} does not exist", req.getParameter("id"));
                resp.setStatus(404);
            } catch (NumberFormatException e) {
                log.error("Invalid id: {}", req.getParameter("id"));
                resp.setStatus(400);
            } catch (ControllerException e) {
                log.error("Error while processing request {}", e.getMessage());
                resp.setStatus(500);
            }
        }
    }
}
