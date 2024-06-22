package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CreditNoteBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CreditNoteDao;
import edu.icet.pos.dto.CreditNote;
import edu.icet.pos.entity.CreditNoteEntity;
import edu.icet.pos.entity.ReturnEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;

public class CreditNoteBoImpl implements CreditNoteBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private final CreditNoteDao creditNoteDao = DaoFactory.getInstance().getDao(DaoType.CREDIT_NOTE);
    @Override
    public boolean save(CreditNote dto) {
        CreditNoteEntity creditNoteEntity = new CreditNoteEntity(
                mapper.map(dto.getReturns(), ReturnEntity.class),
                dto.getPrice(),
                dto.isRedeemed(),
                dto.getCreateDate()
        );
        return creditNoteDao.save(creditNoteEntity);
    }
}
