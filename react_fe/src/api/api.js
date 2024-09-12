import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

export const registerTeam = async (name, league, season, stadium) => {
    try {
        // Đóng gói dữ liệu thành một đối tượng với các trường yêu cầu
        const data = {
            seasonId: season, // Sử dụng tham số truyền vào
            tournamentId: league, // Sử dụng tham số truyền vào
            homeCourt: stadium, // Sử dụng tham số truyền vào
            name: name // Sử dụng tham số truyền vào
        };

        // Gửi yêu cầu POST với đối tượng dữ liệu
        const response = await axios.post(`${API_BASE_URL}/api/v1/teams/registers`, data, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    } catch (error) {
        console.error('Error registering team:', error.response ? error.response.data : error.message);
        throw error;
    }
};
export const registerPlayer = async (data) => {
    try {
        // Gửi yêu cầu POST với đối tượng dữ liệu
        const response = await axios.post(`${API_BASE_URL}/api/v1/players/registers`, data, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        return response;
    } catch (error) {
        console.error('Error registering team:', error.response ? error.response.data : error.message);
        throw error;
    }
};
export const getTournament = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/tournaments`);
        const data = response.data.data; // Giả sử dữ liệu cần từ thuộc tính 'data'

        // In ra dữ liệu trước khi tách
        console.log('Dữ liệu trước khi tách:', data);

        // Tạo một Map từ dữ liệu
        const tournamentMap = new Map();

        // Duyệt qua từng cặp khóa-giá trị và thêm vào Map
        Object.entries(data).forEach(([key, value]) => {
            tournamentMap.set(key, value);
        });

        // In ra bản đồ
        console.log('Bản đồ giải đấu:', tournamentMap);

        return tournamentMap; // Trả về bản đồ
    } catch (error) {
        console.error('Error fetching tournament data:', error);
        throw error; // Đẩy lỗi lên cho nơi gọi
    }
};
export const getSeason = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/seasons`);
        const data = response.data.data; // Giả sử dữ liệu cần từ thuộc tính 'data'

        // In ra dữ liệu trước khi tách
        console.log('Dữ liệu trước khi tách:', data);

        // Tạo một Map từ dữ liệu
        const SeasonMap = new Map();

        // Duyệt qua từng cặp khóa-giá trị và thêm vào Map
        Object.entries(data).forEach(([key, value]) => {
            SeasonMap.set(key, value);
        });

        // In ra bản đồ
        console.log('Bản đồ giải đấu:', SeasonMap);

        return SeasonMap; // Trả về bản đồ
    } catch (error) {
        console.error('Error fetching tournament data:', error);
        throw error; // Đẩy lỗi lên cho nơi gọi
    }
};
export const getTeam = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/teams`);
        const data = response.data.data; // Giả sử dữ liệu cần từ thuộc tính 'data'

        // In ra dữ liệu trước khi tách
        console.log('Dữ liệu trước khi tách:', data);

        // Tạo một Map từ dữ liệu
        const TeamMap = new Map();

        // Duyệt qua từng cặp khóa-giá trị và thêm vào Map
        Object.entries(data).forEach(([key, value]) => {
            TeamMap.set(key, value);
        });

        // In ra bản đồ
        console.log('Bản đồ giải đấu:', TeamMap);

        return TeamMap; // Trả về bản đồ
    } catch (error) {
        console.error('Error fetching tournament data:', error);
        throw error; // Đẩy lỗi lên cho nơi gọi
    }
};
export const getRule = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/rules`);
        const data = response.data.data; // Giả sử dữ liệu cần từ thuộc tính 'data'

        // In ra dữ liệu trước khi tách
        console.log('Dữ liệu trước khi tách:', data);

        // Tạo một Map từ dữ liệu
        const RuleMap = new Map();

        // Duyệt qua từng cặp khóa-giá trị và thêm vào Map
        Object.entries(data).forEach(([key, value]) => {
            RuleMap.set(key, value);
        });

        // In ra bản đồ
        console.log('Bản đồ rule:', RuleMap);

        return RuleMap; // Trả về bản đồ
    } catch (error) {
        console.error('Error fetching tournament data:', error);
        throw error; // Đẩy lỗi lên cho nơi gọi
    }
};
export const updateRule = async (ruleData) => {
    try {
        const token = localStorage.getItem("access_token"); 
        const response = await axios.post(`${API_BASE_URL}/api/v1/rules`, {
            minAge: ruleData.minAge,
            maxAge: ruleData.maxAge,
            minNumOfPlayers: ruleData.minNumOfPlayers,
            maxNumOfPlayers: ruleData.maxNumOfPlayers,
            maxNumOfForeigners: ruleData.maxNumOfForeigners,
            maxTimeToScore: ruleData.maxTimeToScore,
            numOfTypeScore: ruleData.numOfTypeScore,
            winScore: ruleData.winScore,
            loseScore: ruleData.loseScore,
            drawnScore: ruleData.drawnScore,
            rankingOrderDetail: ruleData.rankingOrderDetail
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}` 
                
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error updating rule:', error);
        throw error;
    }
};
export const searchPlayers = async (name, team, dob, type) => {
    try {
        // Đóng gói các tham số cần thiết thành một đối tượng
        const params = {
            ...(team && { teamId: team }),
            ...(name && { name: name }),
            ...(dob && { yearOfBirth: dob }),
            ...(type && { type: type })
        };
        console.log(params);
        // Thực hiện GET request với các tham số query
        const response = await axios.get(`${API_BASE_URL}/api/v1/players`, {
            params: params
        });

        console.log(response.data);  // In ra dữ liệu nhận được từ server
        return response.data.data;  // Trả về dữ liệu từ server
    } catch (error) {
        console.error('Error fetching players:', error.response ? error.response.data : error.message);
        throw error;  // Ném lỗi nếu có
    }
};
export const updatePlayer = async (selectedPlayer) => {
    try {
        const token= localStorage.getItem('access_token');
        const response = await axios.post(`${API_BASE_URL}/api/v1/players`, {
            id: selectedPlayer.id,
            name: selectedPlayer.name,
            dateOfBirth: selectedPlayer.dateOfBirth,
            type: selectedPlayer.type,
            note: selectedPlayer.note
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error updating rule:', error);
        throw error;
    }
};
export const getTeamRank = async (tournament, season) => {
    try {
        // Đóng gói các tham số cần thiết thành một đối tượng
        const params = {
            ...(season && { season_id: season }),
            ...(tournament && { tournament_id: tournament })
        };
        // Thực hiện GET request với các tham số query
        const response = await axios.get(`${API_BASE_URL}/api/v1/tournaments/ranking`, {
            params: params
        });

        console.log(response.data.data);  // In ra dữ liệu nhận được từ server
        return response.data.data;  // Trả về dữ liệu từ server
    } catch (error) {
        console.error('Error fetching players:', error.response ? error.response.data : error.message);
        throw error;  // Ném lỗi nếu có
    }
};
