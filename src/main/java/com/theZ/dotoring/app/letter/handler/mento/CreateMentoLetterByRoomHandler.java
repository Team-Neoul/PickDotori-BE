package com.theZ.dotoring.app.letter.handler.mento;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.mapper.LetterMapper;
import com.theZ.dotoring.app.letter.service.LetterMentoService;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 컨트롤러에게 받은 인자들을 DB로부터 조회를 해와 엔티티로 LetterService에게 넘겨주고, 반환값을 다시 컨트롤러에게 넘겨줍니다.
 *
 * @author Kevin
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class CreateMentoLetterByRoomHandler {

    final private LetterMentoService letterManagementService;

    final private MentoService mentoService;

    final private RoomService roomService;

    /**
     * 컨트롤러로부터 받은 인자들을 이용해 DB로부터 엔티티를 조회한 후, Service에게 넘긴 후 반환 값을 컨트롤러에게 전달하는 메서드
     *
     * @param letterRequestDTO - 클라이언트가 보낸 메세지 내용
     * @param mentoId - 쪽지를 보낸 멘토 id
     * @param roomId - 쪽지를 주고 받은 쪽지함 id

     * @return LetterByMemberResponseDTO를 반환
     */
    @Transactional
    public LetterByMemberResponseDTO execute(LetterByMemberRequestDTO letterRequestDTO, Long mentoId, Long roomId){

        Mento user = mentoService.findMento(mentoId);

        Room room = roomService.findByRoomId(roomId);

        Letter letter = letterManagementService.sendLetterWhereIn(letterRequestDTO, user, room);

        return LetterMapper.INSTANCE.toDTO(letter, user.getNickname());
    }
}
