/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoSkillDetailComponent } from 'app/entities/tipo-skill/tipo-skill-detail.component';
import { TipoSkill } from 'app/shared/model/tipo-skill.model';

describe('Component Tests', () => {
  describe('TipoSkill Management Detail Component', () => {
    let comp: TipoSkillDetailComponent;
    let fixture: ComponentFixture<TipoSkillDetailComponent>;
    const route = ({ data: of({ tipoSkill: new TipoSkill(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoSkillDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoSkill).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
