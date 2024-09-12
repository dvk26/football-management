import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useEffect, useState } from 'react';
import { Alert, Button } from 'react-bootstrap';
import { useFieldArray, useForm } from 'react-hook-form';
import { getSeason, getTeam, getTournament, registerPlayer, registerTeam } from '../api/api';
import bannerImage from '../img/background.jpg';
import './Register.css'; // Import file CSS
export default function App() {
    const [Tournament, setTournament] = useState(new Map());
    const [Season, setSeason] = useState(new Map());
    const [Team, setTeam] = useState(new Map());
    const [Teamid, setTeamid] = useState(1);
    const [Tournamentid, setTournamentid] = useState(1);
    const [Seasonid, setSeasonid] = useState(1);
    const [currentForm, setCurrentForm] = useState(1);
    const [showAlert, setShowAlert] = useState(false);
    const { register, handleSubmit, control, reset, getValues } = useForm({
        defaultValues: {
            players: [{ name: '', dob: '', type: '', notes: '' }]
        }
    });
    const { fields, append } = useFieldArray({
        control,
        name: 'players'
    });

    const handleFormChange = (formNumber) => {
        if (formNumber === 1) {
            reset({
                players: [{ name: '', dob: '', type: '', notes: '' }]
            });
        }
        setCurrentForm(formNumber);
    };

    const onSubmit = async (data) => {
        // ua sao nay no console log ra loi ma
        if (currentForm === 1) {
            try {
                // Gọi API để đăng ký đội bóng
                const valuesArray = Object.values(data);
                console.log(valuesArray);
                const response = await registerTeam(valuesArray[1], valuesArray[2], valuesArray[3], valuesArray[4]);
                // Kiểm tra phản hồi từ server (ví dụ: nếu đăng ký thành công)
                //log response xem
                if (response.status === 200) {
                    // Chuyển sang form tiếp theo nếu thành công
                    const t = await getTeam();
                    setTeam(t)
                    setTournamentid(valuesArray[2])
                    setSeasonid(valuesArray[3])
                    console.log(Tournamentid, Seasonid)
                    console.log(valuesArray[1]);
                    console.log(t.get(valuesArray[1]));
                    setTeamid(t.get(valuesArray[1]))
                    console.log(Teamid)
                    setCurrentForm(currentForm + 1);
                }
                else{
                    console.log("response: "+response.message)
                }
                

            } catch (error) {
                alert('Đăng ký thất bại!')
                console.error('Đăng ký thất bại:', error);
                // Nế
            }
        } else {
            // Xử lý khi ở form thứ 2, ví dụ: hiển thị thông báo thành công
            console.log(data)
            let array = [];
            for (let i = 0; i < data.players.length; i++) {
                const player = data.players[i];
                let value = {
                    teamId: Teamid,
                    tournamentId: Tournamentid,
                    seasonId: Seasonid,
                    name: player.name,
                    dateOfBirth: player.dob,
                    type: player.type,
                    note: player.note
                }
                console.log(Teamid, Tournamentid, Seasonid, player.name, player.dob, player.type, player.note)
                array.push(value)
            }
            console.log(array)
            try {
                const response = await registerPlayer(array);
                if (response.status == 200) {
                    setShowAlert(true);
                    reset({
                        players: [{ name: '', dob: '', type: '', notes: '' }]
                    });

                    setCurrentForm(1);
                }

            } catch (error) {
                alert('Đăng ký thất bại:')
                console.error('Đăng ký thất bại:', error);
            }

        };
    }

    const handleCloseAlert = () => setShowAlert(false);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const tnm = await getTournament();
                const ss = await getSeason();
                setTournament(tnm); // Cập nhật state với mảng dữ liệu
                setSeason(ss);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);
    return (
        <>
            <div className="banner" style={{ backgroundImage: `url(${bannerImage})` }}>
                <h1>Đăng ký đội bóng</h1>
            </div>
            <form onSubmit={handleSubmit(onSubmit)} className="form-container">
                {currentForm === 1 && (
                    <>

                        <div className="form-group">
                            <label htmlFor="TeamName">Tên đội bóng</label>
                            <input id="TeamName" {...register("TeamName")} />
                        </div>
                        <div className="form-group">
                            <label htmlFor="League">Giải đấu</label>
                            <select id="League" {...register("League")}>
                            <option value="">Chọn giải đấu</option>
                                {Array.from(Tournament.entries()).map(([name, id]) => (
                                    <option key={id} value={id}>
                                        {name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="Season">Mùa giải</label>
                            <select id="Season" {...register("Season")}>
                                <option value="">Chọn mùa giải</option>
                                {Array.from(Season.entries()).map(([name, id]) => (
                                    
                                    <option key={id} value={id}>
                                        {name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="Stadium">Sân vận động</label>
                            <input id="Stadium" {...register("Stadium")} />
                        </div>
                    </>
                )}
                {currentForm === 2 && (
                    <div>
                        <h2>Đăng ký cầu thủ</h2>
                        <div className="player-table">
                            <div className="player-header">
                                <div>Số thứ tự</div>
                                <div>Tên cầu thủ</div>
                                <div>Ngày sinh</div>
                                <div>Loại</div>
                                <div>Ghi chú</div>
                            </div>
                            {fields.map((item, index) => (
                                <div key={item.id} className="player-row">
                                    <div>{index + 1}</div>
                                    <div>
                                        <input
                                            {...register(`players.${index}.name`)}
                                            placeholder="Tên cầu thủ"
                                        />
                                    </div>
                                    <div>
                                        <input
                                            type="date"
                                            {...register(`players.${index}.dob`)}
                                        />
                                    </div>
                                    <div>
                                        <select {...register(`players.${index}.type`)}>
                                            <option value="FORIEGN">Cầu thủ nước ngoài</option>
                                            <option value="NATIONAL">Cầu thủ trong nước</option>
                                        </select>
                                    </div>
                                    <div>
                                        <input
                                            {...register(`players.${index}.notes`)}
                                            placeholder="Ghi chú"
                                        />
                                    </div>
                                </div>
                            ))}
                        </div>
                        <Button
                            variant="primary"
                            onClick={() => {
                                append({ name: '', dob: '', type: '', notes: '' })
                                console.log(getValues('players'));
                            }}
                        >
                            Thêm cầu thủ
                        </Button>
                    </div>
                )}
                <div className="button-container">
                    {currentForm > 1 && (
                        <Button variant="secondary" onClick={() => handleFormChange(currentForm - 1)}>
                            Quay lại
                        </Button>
                    )}
                    <Button type="submit">
                        {currentForm === 1 ? 'Tiếp' : 'Gửi'}
                    </Button>
                </div>
            </form>

            {/* Alert thông báo */}
            {showAlert && (
                <div className="alert-container">
                    <Alert variant="success" onClose={handleCloseAlert} dismissible className="alert-custom">
                        <Alert.Heading>Đăng ký thành công!</Alert.Heading>
                        <div className="alert-body">
                            <div className="alert-button-container">
                                <Button className="alert-button" onClick={handleCloseAlert}>
                                    Xác nhận
                                </Button>
                            </div>
                        </div>
                    </Alert>
                </div>
            )}
        </>
    );
}

