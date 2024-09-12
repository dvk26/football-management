// Results.js

import React, { useState } from 'react';
import { Form, Table, Button, Modal } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Results.css'; // Import file CSS riêng

const Results = () => {
    const [selectedLeague, setSelectedLeague] = useState('');
    const [selectedSeason, setSelectedSeason] = useState('');
    const [selectedRound, setSelectedRound] = useState('');
    const [matches, setMatches] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [currentMatch, setCurrentMatch] = useState(null);

    // Dữ liệu mẫu cho các sự kiện
    const matchEvents = [
        { id: 1, player: 'Player A', team: 'Team A', eventType: 'Bàn thắng', time: '15\'' },
        { id: 2, player: 'Player B', team: 'Team B', eventType: 'Phạt đền', time: '45\'' }
    ];

    const handleLeagueChange = (event) => {
        setSelectedLeague(event.target.value);
        setSelectedSeason('');
        setSelectedRound('');
        setMatches([]);
    };

    const handleSeasonChange = (event) => {
        setSelectedSeason(event.target.value);
        setSelectedRound('');
        setMatches([]);
    };

    const handleRoundChange = (event) => {
        setSelectedRound(event.target.value);
        // Dữ liệu mẫu cho các trận đấu
        setMatches([
            { id: 1, homeTeam: 'Team A', awayTeam: 'Team B', homeScore: 2, awayScore: 0, date: '2024-07-20', time: '15:00', venue: 'Stadium A' },
            { id: 2, homeTeam: 'Team C', awayTeam: 'Team D', homeScore: 1, awayScore: 1, date: '2024-07-21', time: '18:00', venue: 'Stadium B' }
        ]);
    };

    const handleMatchClick = (match) => {
        setCurrentMatch({ ...match, events: matchEvents }); // Thêm sự kiện vào match
        setShowModal(true);
    };

    const handleCloseModal = () => setShowModal(false);

    return (
        <>
            <div className="results-container">
                <div className="results-controls">
                    <Form.Group>
                        <Form.Label>Chọn giải đấu:</Form.Label>
                        <Form.Control as="select" onChange={handleLeagueChange} value={selectedLeague}>
                            <option value="">Chọn...</option>
                            <option value="League1">League1</option>
                            <option value="League2">League2</option>
                            <option value="League3">League3</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Chọn mùa giải:</Form.Label>
                        <Form.Control as="select" onChange={handleSeasonChange} value={selectedSeason} disabled={!selectedLeague}>
                            <option value="">Chọn...</option>
                            <option value="2023">2023</option>
                            <option value="2022">2022</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Chọn vòng đấu:</Form.Label>
                        <Form.Control as="select" onChange={handleRoundChange} value={selectedRound} disabled={!selectedSeason}>
                            <option value="">Chọn...</option>
                            <option value="Round1">Vòng 1</option>
                            <option value="Round2">Vòng 2</option>
                        </Form.Control>
                    </Form.Group>
                </div>

                <Table striped bordered hover className="results-table">
                    <thead>
                        <tr>
                            <th>Kết quả</th>
                        </tr>
                    </thead>
                    <tbody>
                        {matches.map((match) => (
                            <tr key={match.id} onClick={() => handleMatchClick(match)}>
                                <td>{`${match.homeTeam} ${match.homeScore}:${match.awayScore} ${match.awayTeam}`}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>

            <Modal show={showModal} onHide={handleCloseModal} className="custom-modal">
                <Modal.Header closeButton>
                    <Modal.Title>Chi tiết trận đấu</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {currentMatch ? (
                        <div className="match-details">
                            <div className="match-info">
                                <div className="team-info">
                                    <p><strong>Đội nhà:</strong> {currentMatch.homeTeam}</p>
                                    <p><strong>Đội khách:</strong> {currentMatch.awayTeam}</p>
                                </div>
                                <div className="score-venue">
                                    <p><strong>Kết quả:</strong> {currentMatch.homeScore} : {currentMatch.awayScore}</p>
                                    <p><strong>Sân:</strong> {currentMatch.venue}</p>
                                </div>
                            </div>
                            <div className="date-time">
                                <p><strong>Ngày:</strong> {currentMatch.date}</p>
                                <p><strong>Giờ:</strong> {currentMatch.time}</p>
                            </div>
                            <div className="match-events">
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên cầu thủ</th>
                                            <th>Đội</th>
                                            <th>Loại bàn thắng</th>
                                            <th>Thời điểm</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {currentMatch.events.map((event) => (
                                            <tr key={event.id}>
                                                <td>{event.id}</td>
                                                <td>{event.player}</td>
                                                <td>{event.team}</td>
                                                <td>{event.eventType}</td>
                                                <td>{event.time}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </Table>
                            </div>
                        </div>
                    ) : (
                        <p>Không có dữ liệu để hiển thị.</p>
                    )}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Đóng
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default Results;
