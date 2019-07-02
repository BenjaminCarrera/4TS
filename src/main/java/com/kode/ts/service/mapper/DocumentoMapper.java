package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.DocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documento} and its DTO {@link DocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentoMapper extends EntityMapper<DocumentoDTO, Documento> {



    default Documento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(id);
        return documento;
    }
}
