/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { NivelPerfilDetailComponent } from 'app/entities/nivel-perfil/nivel-perfil-detail.component';
import { NivelPerfil } from 'app/shared/model/nivel-perfil.model';

describe('Component Tests', () => {
  describe('NivelPerfil Management Detail Component', () => {
    let comp: NivelPerfilDetailComponent;
    let fixture: ComponentFixture<NivelPerfilDetailComponent>;
    const route = ({ data: of({ nivelPerfil: new NivelPerfil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [NivelPerfilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NivelPerfilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelPerfilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nivelPerfil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
