import React, { useState, useEffect } from 'react';
import { Form, Button, Table, Modal } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Schedule.css';
import axios from 'axios';

const Schedule = () => {
    const [currentLeague, setCurrentLeague] = useState('');
    const [currentSeason, setCurrentSeason] = useState('');
    const [newSeason, setNewSeason] = useState('');
    const [newLeague, setNewLeague] = useState(''); // Thêm trạng thái cho giải đấu mới
    const [leagues, setLeagues] = useState([]);
    const [seasons, setSeasons] = useState([]);
    const [schedule, setSchedule] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [seasonDetails, setSeasonDetails] = useState([]);
    const [tournamentDetails, setTournamentDetails] = useState([]);
    const [currentMatch, setCurrentMatch] = useState(null);
    const [startSeason, setStartSeason] = useState('');  // Thêm trạng thái cho ngày bắt đầu
    const [endSeason, setEndSeason] = useState('');
    useEffect(() => {
        const render = async () => {
            const seasonsResponse = await fetch('http://localhost:8080/api/v1/seasons');
            const seasonData = await seasonsResponse.json();
            setSeasonDetails(seasonData.data);

            const seasons = Object.keys(seasonData.data);
            setSeasons(seasons);

            const tournamentsResponse = await fetch('http://localhost:8080/api/v1/tournaments');
            const tournamentData = await tournamentsResponse.json();
            setTournamentDetails(tournamentData.data);
            const tournaments = Object.keys(tournamentData.data);
            setLeagues(tournaments);
        };
        render();
    }, []);

    const handleSelectLeague = (event) => {
        setCurrentLeague(event.target.value);
        setCurrentSeason('');
    };

    const handleSelectSeason = (event) => {
        setCurrentSeason(event.target.value);
    };

    const getSchedule = async () => {
        const response = await axios.get('http://localhost:8080/api/v1/matches', {
            params: {
                season_id: seasonDetails[currentSeason],
                tournament_id: tournamentDetails[currentLeague],
            },
            headers: {
                'Content-Type': 'application/json',
            }
        });
        const scheduleDetail = response.data.data.map(match => ({
            round: match.round,
            matches: [{
                matchId: match.id,
                homeTeam: match.homeTeamString,
                homeTeamId: match.homeTeamId,
                awayTeamId: match.awayTeamId,
                homeTeamPlayers: match.homeTeamPlayers,
                awayTeam: match.awayTeamString,
                awayTeamPlayers: match.awayTeamPlayers,
                score1Team: match.team1Score,
                score2Team: match.team2Score,
                date: match.date,
                time: match.time,
                venue: match.venue,
                events: match.events || []
            }],
        }));
        if (scheduleDetail.length === 0) {
            alert("Mùa giải chưa được xếp lịch. Tiếp tục xếp lịch.")
            querySchedule();
            return;
        }

        const groupedByRound = scheduleDetail.reduce((acc, match) => {
            const existingRound = acc.find(round => round.round === match.round);
            if (existingRound) {
                existingRound.matches.push(...match.matches);
            } else {
                acc.push(match);
            }
            return acc;
        }, []);
        setSchedule(groupedByRound);
    }

    const querySchedule = async () => {
        // const linking = await axios.get('http://localhost:8080/api/v1/tournaments/linking', {
        //     params: {
        //         season_id: seasonDetails[currentSeason],
        //         tournament_id: tournamentDetails[currentLeague],
        //     },
        // });
        const token = localStorage.getItem('access_token'); 
        const response = await axios.get('http://localhost:8080/api/v1/tournaments/schedule', {
            params: {
                season_id: seasonDetails[currentSeason],
                tournament_id: tournamentDetails[currentLeague],
            }});
        const scheduleDetail = response.data.data.map(match => ({
            round: match.round,
            matches: [{
                matchId: match.id,
                homeTeam: match.homeTeamString,
                homeTeamId: match.homeTeamId,
                awayTeamId: match.awayTeamId,
                homeTeamPlayers: match.homeTeamPlayers,
                awayTeam: match.awayTeamString,
                awayTeamPlayers: match.awayTeamPlayers,
                score1Team: match.team1Score,
                score2Team: match.team2Score,
                date: match.date,
                time: match.time,
                venue: match.venue,
                events: match.events || []
            }],
        }));

        const groupedByRound = scheduleDetail.reduce((acc, match) => {
            const existingRound = acc.find(round => round.round === match.round);
            if (existingRound) {
                existingRound.matches.push(...match.matches);
            } else {
                acc.push(match);
            }
            return acc;
        }, []);

        setSchedule(groupedByRound);
    };

    const handleMatchClick = (match) => {
        setCurrentMatch({ ...match });
        setShowModal(true);
    };

    const handleCloseModal = () => setShowModal(false);

    const updateEvent = (index, updatedData) => {
        const updatedEvents = [...currentMatch.events];
        updatedEvents[index] = { ...updatedEvents[index], ...updatedData };
        setCurrentMatch({ ...currentMatch, events: updatedEvents });
    };

    const saveMatchData = async () => {
        try {
            const token = localStorage.getItem('access_token');
            const payload = currentMatch.events.map(event => ({
                matchId: currentMatch.matchId,
                playerId: event.player,  // Assuming event.player holds playerId
                teamId: event.team,      // Assuming event.team holds teamId
                goalType: event.eventType, // Parse goalType to integer
                timeScore: parseInt(event.time, 10),     // Parse timeScore to integer
            }));

            console.log("Payload to send:", payload);

            await axios.post(`http://localhost:8080/api/v1/matches/goal`,{

                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` 
    
                }
            }, payload);
            for (const event of currentMatch.events) {
                await axios.get(`http://localhost:8080/api/v1/matches/goal/conceded`, {
                    params: {
                        season_id: seasonDetails[currentSeason],
                        tournament_id: tournamentDetails[currentLeague],
                        team_id: event.team === currentMatch.homeTeamId ? currentMatch.awayTeamId : currentMatch.homeTeamId
                    }});
            }
            getSchedule();
            alert('Match data saved successfully!');
        } catch (error) {
            console.error('Error saving match data:', error);
            alert('Failed to save match data.');
        }
    };

    const addNewEvent = () => {
        const newEvent = {
            id: Date.now(),
            player: '',
            team: '',
            eventType: '',
            time: 0,
        };
        setCurrentMatch(prevState => ({
            ...prevState,
            events: [...prevState.events, newEvent],
        }));
    };

    const deleteEvent = (index) => {
        const updatedEvents = currentMatch.events.filter((_, i) => i !== index);
        setCurrentMatch({ ...currentMatch, events: updatedEvents });
    };

    const handleCreateNewSeason = async () => {
        try {
            console.log(newSeason, startSeason, endSeason)
            const token= localStorage.getItem('access_token');
            await axios.post('http://localhost:8080/api/v1/seasons', { name: newSeason, start: startSeason, end: endSeason,
                 },{headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
    
                }});
            setNewSeason('');
            alert('Season created successfully!');
            // Refresh the seasons list
            const seasonsResponse = await fetch('http://localhost:8080/api/v1/seasons');
            const seasonData = await seasonsResponse.json();
            setSeasonDetails(seasonData.data);
            const seasons = Object.keys(seasonData.data);
            setSeasons(seasons);
        } catch (error) {
            console.error('Error creating season:', error);
            alert('Failed to create season.');
        }
    };

    const handleCreateNewLeague = async () => {
        try {
            const token= localStorage.getItem('access_token');
            await axios.post('http://localhost:8080/api/v1/tournaments',
                 { name: newLeague,},
                 { headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }});
            setNewLeague('');
            alert('League created successfully!');
            // Refresh the leagues list
            const tournamentsResponse = await fetch('http://localhost:8080/api/v1/tournaments');
            const tournamentData = await tournamentsResponse.json();
            setTournamentDetails(tournamentData.data);
            const tournaments = Object.keys(tournamentData.data);
            setLeagues(tournaments);
        } catch (error) {
            console.error('Error creating league:', error);
            alert('Failed to create league.');
        }
    };

    return (
        <>
            <h3>Lịch thi đấu</h3>
            <div className="schedule-container">
                <div className="schedule-controls">
                    <Form.Group>
                        <Form.Label>Chọn giải đấu:</Form.Label>
                        <Form.Control as="select" onChange={handleSelectLeague} value={currentLeague}>
                            <option value="">Chọn...</option>
                            {leagues.map((league) => (
                                <option key={league} value={league}>
                                    {league}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Chọn mùa giải:</Form.Label>
                        <Form.Control as="select" onChange={handleSelectSeason} value={currentSeason} disabled={!currentLeague}>
                            <option value="">Chọn...</option>
                            {seasons.map((season) => (
                                <option key={season} value={season}>
                                    {season}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Button variant="primary" onClick={getSchedule}>
                        Xem lịch thi đấu
                    </Button>
                </div>

                <div className="create-new-container">
                    <Form.Group>
                        <Form.Label>Tạo mùa giải mới:</Form.Label>
                        <Form.Control
                            type="text"
                            value={newSeason}
                            onChange={(e) => setNewSeason(e.target.value)}
                            placeholder="Nhập tên mùa giải mới"
                        />
                        <Form.Control
                            type="text"
                            value={startSeason}
                            onChange={(e) => setStartSeason(e.target.value)}
                            placeholder="Nhập thời gian bắt đầu"
                        />
                        <Form.Control
                            type="text"
                            value={endSeason}
                            onChange={(e) => setEndSeason(e.target.value)}
                            placeholder="Nhập thời kết thúc"
                        />
                        <Button variant="success" onClick={handleCreateNewSeason}>
                            Tạo Mùa Giải
                        </Button>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Tạo giải đấu mới:</Form.Label>
                        <Form.Control
                            type="text"
                            value={newLeague}
                            onChange={(e) => setNewLeague(e.target.value)}
                            placeholder="Nhập tên giải đấu mới"
                        />
                        <Button variant="success" onClick={handleCreateNewLeague}>
                            Tạo Giải Đấu
                        </Button>
                    </Form.Group>
                </div>

                {schedule.length > 0 && (
                    <div className="schedule-table-container">
                        {schedule.map((round, index) => (
                            <div key={index} className="round-schedule">
                                <h4>Vòng {round.round}</h4>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>Đội nhà</th>
                                            <th></th>
                                            <th></th>
                                            <th>Đội khách</th>
                                            <th>Ngày</th>
                                            <th>Giờ</th>
                                            <th>Sân vận động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {round.matches.map((match, idx) => (
                                            <tr key={idx} onClick={() => handleMatchClick(match)}>
                                                <td>{match.homeTeam}</td>
                                                <td>{match.score1Team}</td>
                                                <td>{match.score2Team}</td>
                                                <td>{match.awayTeam}</td>
                                                <td>{match.date}</td>
                                                <td>{match.time}</td>
                                                <td>{match.venue}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </Table>
                            </div>
                        ))}
                    </div>
                )}
            </div>
            {currentMatch && (
                <Modal show={showModal} onHide={handleCloseModal} className="custom-modal">
                    <Modal.Header closeButton>
                        <Modal.Title>Bàn thắng</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="match-details">
                            <div className="match-events">
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên cầu thủ</th>
                                            <th>Đội</th>
                                            <th>Loại bàn thắng</th>
                                            <th>Thời điểm (phút)</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {currentMatch.events.map((event, index) => (
                                            <tr key={event.id}>
                                                <td>{index + 1}</td>
                                                <td>
                                                    <Form.Control
                                                        as="select"
                                                        value={event.player}
                                                        onChange={(e) => updateEvent(index, { player: e.target.value })}
                                                    >
                                                        <option>Chọn...</option>
                                                        {currentMatch.homeTeamPlayers.concat(currentMatch.awayTeamPlayers).map((player) => (
                                                            <option key={player.id} value={player.id}>
                                                                {player.name}
                                                            </option>
                                                        ))}
                                                    </Form.Control>
                                                </td>
                                                <td>
                                                    <Form.Control
                                                        as="select"
                                                        value={event.team}
                                                        onChange={(e) => updateEvent(index, { team: e.target.value })}
                                                    >
                                                        <option>Chọn...</option>
                                                        <option value={currentMatch.homeTeamId}>{currentMatch.homeTeam}</option>
                                                        <option value={currentMatch.awayTeamId}>{currentMatch.awayTeam}</option>
                                                    </Form.Control>
                                                </td>
                                                <td>
                                                    <Form.Control
                                                        as="select"
                                                        value={event.eventType}
                                                        onChange={(e) => updateEvent(index, { eventType: parseInt(e.target.value, 10) })}
                                                    >
                                                        <option>Chọn...</option>
                                                        <option value={1}>Bàn Thắng</option>
                                                        <option value={2}>Phạt đền</option>
                                                        <option value={3}>Phản lưới</option>
                                                    </Form.Control>
                                                </td>
                                                <td>
                                                    <Form.Control
                                                        type="number"
                                                        min="0"
                                                        max="90"
                                                        value={event.time}
                                                        onChange={(e) => updateEvent(index, { time: e.target.value })}
                                                    />
                                                </td>
                                                <td>
                                                    <Button variant="danger" onClick={() => deleteEvent(index)}>
                                                        Xóa
                                                    </Button>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </Table>
                                <Button variant="primary" onClick={addNewEvent}>
                                    Thêm bàn thắng
                                </Button>
                            </div>
                        </div>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Đóng
                        </Button>
                        <Button variant="primary" onClick={saveMatchData}>
                            Lưu
                        </Button>
                    </Modal.Footer>
                </Modal>
            )}
        </>
    );
};

export default Schedule;