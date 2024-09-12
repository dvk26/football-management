import React, { useState } from 'react';
import { Table, Button, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Rankings.css'; // Import file CSS riêng

const Rankings = () => {
    const [currentLeague, setCurrentLeague] = useState('');
    const [currentSeason, setCurrentSeason] = useState('');
    const [leagues, setLeagues] = useState(['League 1', 'League 2']); // Danh sách giải đấu mẫu
    const [seasons, setSeasons] = useState(['2023', '2022']); // Danh sách mùa giải mẫu
    const [teams, setTeams] = useState([]); // Dữ liệu bảng xếp hạng

    const handleSelectLeague = (event) => {
        setCurrentLeague(event.target.value);
    };

    const handleSelectSeason = (event) => {
        setCurrentSeason(event.target.value);

        // Lấy dữ liệu bảng xếp hạng cho giải đấu và mùa giải được chọn (dữ liệu mẫu)
        if (currentLeague && event.target.value) {
            if (event.target.value === '2023') {
                setTeams([
                    { rank: 1, name: 'Team A', matches: 10, wins: 8, draws: 1, losses: 1, goalsFor: 20, goalsAgainst: 5, goalDifference: 15, points: 25 },
                    { rank: 2, name: 'Team B', matches: 10, wins: 7, draws: 2, losses: 1, goalsFor: 18, goalsAgainst: 6, goalDifference: 12, points: 23 },
                ]);
            } else if (event.target.value === '2022') {
                setTeams([
                    { rank: 1, name: 'Team C', matches: 10, wins: 9, draws: 1, losses: 0, goalsFor: 22, goalsAgainst: 4, goalDifference: 18, points: 28 },
                    { rank: 2, name: 'Team D', matches: 10, wins: 6, draws: 3, losses: 1, goalsFor: 15, goalsAgainst: 8, goalDifference: 7, points: 21 },
                ]);
            }
        }
    };
    return (
        <>
            <div className="ranking-container">
                <div className="ranking-controls">
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
                </div>

                <Table striped bordered hover className="ranking-table">
                    <thead>
                        <tr>
                            <th>Hạng</th>
                            <th>Tên đội</th>
                            <th>Số trận</th>
                            <th>Thắng</th>
                            <th>Hòa</th>
                            <th>Thua</th>
                            <th>Bàn thắng</th>
                            <th>Bàn thua</th>
                            <th>Hiệu số</th>
                            <th>Điểm</th>
                        </tr>
                    </thead>
                    <tbody>
                        {teams.map((team, index) => (
                            <tr key={index}>
                                <td>{team.rank}</td>
                                <td>{team.name}</td>
                                <td>{team.matches}</td>
                                <td>{team.wins}</td>
                                <td>{team.draws}</td>
                                <td>{team.losses}</td>
                                <td>{team.goalsFor}</td>
                                <td>{team.goalsAgainst}</td>
                                <td>{team.goalDifference}</td>
                                <td>{team.points}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
        </>
    );
};

export default Rankings;
