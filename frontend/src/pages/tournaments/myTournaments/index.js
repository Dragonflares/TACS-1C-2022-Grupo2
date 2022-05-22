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
import { getTournaments, getTournamentsCount } from "../../../services/userService";
import { ToastContainer, toast } from 'react-toastify';

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
            name: '',
            owner: {username: ''},
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

    const getData = (page, pageSize) =>  {
        getTournaments(page, pageSize).then(
            response => {
                setData(p => (
                    {
                        ...p,
                        elements: response.data.response,
                    }
                ))

                getTournamentsCount().then(
                    resp => {
                        setData(p => ({
                            ...p,
                            count: resp.data.response,
                        }))
                    }
                ).catch( e => {
                        toast.error(e.response.data.response.message);
                    })         
            }
        ).catch(e => {
                toast.error(e.response.data.response.message);
            })
    };

    useEffect(() => {
        const init = async () => {
            getData(1, pageSize);
        };

        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        getData(page, pageSize);
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
            <ToastContainer/>
        </>
    )
}

export default MyTournaments;