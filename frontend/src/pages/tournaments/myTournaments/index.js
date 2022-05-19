import React, {useState, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import { PaginatedTable } from "../../../shared/components/paginatedTable";
import { Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import OptionsPopUp from "./optionsPopUp";
import { getPublicTournaments } from "../../../services/tournamentService";
import { getTournaments } from "../../../services/userService";

export function MyTournaments () {
    const pageSize = 10;
    const [data, setData] = useState({
        elements: [],
        count: 10,
    });
    const [show, setShow] = useState(false);
    const [selected, setSelected] = useState(
        {
            id: 0,
            name: ''
        }
    );

    const headings = [
        {   
            name: 'name',
            show: 'Name'
        },
        {
            name: 'language',
            show: 'Lang',
        },
        {
            name: 'startDate',
            show: 'Start Date'
        },
        {
            name: 'endDate',
            show: 'End Date'
        },
        {
            name: 'privacy',
            show: 'Privacy'
        }
    ];

    const getData = async (page, pageSize) =>  {
        

       //const offset = ((page - 1) * pageSize);
        //page = 1
        getTournaments(1, pageSize).then(
            response => {
                if(response.status === 200){
                    setData({
                        elements: response.data.response,
                        count: 100,
                    });
                }
            }
        );
        
        /* MOCK SIN API
            const elements = [];
        for(let i = offset + 1; i < offset + pageSize + 1; i++){
            elements.push({
                id: i,
                name: `tourn nr0 ${i}`,
                owner: 'pepe',
                language: 'english',
                ongoing: 'Si',
                ownerId: 1,
            });
        }

        const mock = {
            elements : elements,
            count : 100,
        }

        setData(mock);
          
         */
    };

    useEffect(() => {
        const init = async () => {
            await getData(1, pageSize);
        };

        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        await getData(page, pageSize);
    });

    const handleRowClick = useCallback((element) => {
        setSelected(element);
        setShow(true);
    });

    const handleHide = useCallback(() => {
        setShow(false);
    });

    return (
        <>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card>
                        <Card.Body>
                            <Card.Title>My Tournaments</Card.Title>
                            <Row>
                                <Button as={Link} to={"/tournament/create"} variant="primary" style={{float: 'left'}}>
                                    Add
                                </Button>
                            </Row>
                            <Row>
                                {
                                    data.count > 0 ?
                                    <>
                                        <PaginatedTable 
                                            headings={headings}
                                            data={data}
                                            pageSize={pageSize}
                                            onPageChange={handlePageChange}
                                            onClick={handleRowClick}
                                            key='myTournaments'
                                        />  
                                    </>
                                    :
                                    <h3>NO TOURNAMENTS</h3>
                                } 
                            </Row>                                                     
                        </Card.Body>
                    </Card>
                </Container>
            </Col>
            <OptionsPopUp show={show} selected={selected} handleClose={handleHide}/>  
        </>
    )
}

export default MyTournaments;