import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useEffect, useState } from 'react';
import { Form, Table } from 'react-bootstrap';
import { getSeason, getTeamRank, getTournament } from '../api/api'; // Import các API
import './Rankings.css'; // Import file CSS riêng

const Rankings = () => {
    const [currentLeague, setCurrentLeague] = useState('');
    const [currentSeason, setCurrentSeason] = useState('');
    const [tournament, setTournament] = useState(new Map());
    const [seasons, setSeasons] = useState(new Map());
    const [teams, setTeams] = useState([]);

    // Fetch leagues and seasons on component mount
    useEffect(() => {
        const fetchData = async () => {
            try {
                const tnm = await getTournament();
                const ss = await getSeason();
                setTournament(tnm);
                setSeasons(ss);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const handleSelectLeague = (event) => {
        setCurrentLeague(event.target.value);
        setCurrentSeason(''); // Reset season khi chọn league khác
        setTeams([]); // Reset teams khi chọn league khác
    };

    const handleSelectSeason = async (event) => {
        setCurrentSeason(event.target.value);

        if (currentLeague && event.target.value) {
            try {
                console.log(currentLeague, event.target.value)
                const teamMap = await getTeamRank(currentLeague, event.target.value);
                setTeams(Array.from(teamMap.values())); // Assuming teamMap is a map of team data
            } catch (error) {
                console.error('Error fetching team rankings:', error);
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
                            {Array.from(tournament.entries()).map(([name, id]) => (
                                <option key={id} value={id}>
                                    {name}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Chọn mùa giải:</Form.Label>
                        <Form.Control as="select" onChange={handleSelectSeason} value={currentSeason} disabled={!currentLeague}>
                            <option value="">Chọn...</option>
                            {Array.from(seasons.entries()).map(([name, id]) => (
                                <option key={id} value={id}>
                                    {name}
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
                                <td>{index + 1}</td>
                                <td>{team.name}</td>
                                <td>{team.played}</td>
                                <td>{team.won}</td>
                                <td>{team.drawn}</td>
                                <td>{team.lost}</td>
                                <td>{team.scored}</td>
                                <td>{team.conceded}</td>
                                <td>{team.scored - team.conceded}</td>
                                <td>{team.point}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
        </>
    );
};

export default Rankings;
